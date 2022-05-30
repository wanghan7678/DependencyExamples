package com.example.wanghan.service;

import com.example.wanghan.model.InstallationCandidate;
import com.example.wanghan.util.DependencyTree;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DependencyServiceTest {
    String line1 = "a.1,b.2";
    String line2 = "a.1,b.3";
    String line3 = "b.2,c.3";
    String line4 = "c.3,d.5";
    String line5 = "b.2,c.4";
    String line6 = "c.4,b.3";
    String line7 = "d.5,e.7";
    String line8 = "b.2,f.1";

    InstallationCandidate candidateA1 = new InstallationCandidate("a", "1");
    InstallationCandidate candidateB2 = new InstallationCandidate("b", "2");
    InstallationCandidate candidateB3 = new InstallationCandidate("b", "3");
    InstallationCandidate candidateC3 = new InstallationCandidate("c", "3");
    InstallationCandidate candidateD5 = new InstallationCandidate("d", "5");
    InstallationCandidate candidateC4 = new InstallationCandidate("c", "4");
    InstallationCandidate candidateE7 = new InstallationCandidate("e", "7");
    InstallationCandidate candidateF1 = new InstallationCandidate("f", "1");



    @Test
    public void test_checkDependency()
    {
        DependencyTree testTree = new DependencyTree();

        testTree.put(line1, candidateB2);
        testTree.put(line2, candidateB3);
        testTree.put(line3, candidateC3);
        testTree.put(line4, candidateD5);
        testTree.put(line5, candidateC4);
        testTree.put(line6, candidateB3);
        testTree.put(line7, candidateE7);
        testTree.put(line8, candidateF1);

        DependencyService dependencyService = new DependencyService();
        List<InstallationCandidate> installations = new ArrayList<>();
        installations.add(candidateB2);
        installations.add(candidateA1);

        boolean result = dependencyService.checkDependency(candidateA1, installations, testTree);

        assertFalse(result);
    }
}
