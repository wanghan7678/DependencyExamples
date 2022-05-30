package com.example.wanghan.service;


import com.example.wanghan.model.InstallationCandidate;
import com.example.wanghan.util.DataReader;
import com.example.wanghan.util.DataReaderRegister;
import com.example.wanghan.util.DependencyTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DependencyService
{
    private static final Logger logger = LogManager.getLogger(DependencyService.class);

    //TODO: to be autowired
    final private DataReader reader = DataReaderRegister.getReader("txt");


    public Boolean checkDependencies(String filePath) throws Exception
    {
        File file = new File(filePath);

        List<InstallationCandidate> installations = (List<InstallationCandidate>) reader.parseInstallationPart(file);
        DependencyTree dependencyTree = (DependencyTree) reader.parseDependencyPart(file);
        for(InstallationCandidate installationCandidate : installations)
        {
            if (!checkDependency(installationCandidate, installations, dependencyTree))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * check the installation dependencies:
     *      get the installation's direct dependencies,
     *      if the dependency not exists in installation package, return false
     *      else nesting check the dependencies of the direct dependency
     *
     * @param installationCandidate
     * @param installations
     * @param dependencyTree
     * @return
     */
    protected boolean checkDependency(InstallationCandidate installationCandidate, List<InstallationCandidate> installations, DependencyTree dependencyTree)
    {
        List<DependencyTree> children = new ArrayList<>();
        dependencyTree.getChildren(installationCandidate.getKey(), dependencyTree, children);
        List<InstallationCandidate> existsChildren = findExistsInChildren(children, installations);
        if (!children.isEmpty() && existsChildren.isEmpty())
        {
            return false;
        }
        else
        {
            for(InstallationCandidate child : existsChildren)
            {
                return checkDependency(child, installations, dependencyTree);
            }
        }
        return true;
    }

    /**
     * this method iterates all installations, and return the ones exist in the children.
     * @param children
     * @param installations
     * @return
     */
    private List<InstallationCandidate> findExistsInChildren(List<DependencyTree> children, List<InstallationCandidate> installations)
    {
        return installations.stream()
                .filter(installationCandidate ->
                    children.stream()
                            .anyMatch(dependencyTree -> dependencyTree.getKey().equals(installationCandidate.getKey()))
                )
                .collect(Collectors.toList());
    }
}
