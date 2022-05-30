package com.example.wanghan.util;

import com.example.wanghan.exceptions.IllegalInputFormatException;
import com.example.wanghan.model.InstallationCandidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataTxtReader implements DataReader
{
    private static final Logger logger = LogManager.getLogger(DataTxtReader.class);

    @Override
    public Object parseDependencyPart(File inputFile) throws Exception
    {
        return parseDependencies(readLines(inputFile));
    }

    //TODO: to be added
    @Override
    public Object parseDependencyPart(String source) throws Exception
    {
        return null;
    }

    //TODO: to be added.
    @Override
    public Object parseInstallationPart(String source) throws Exception
    {
        return null;
    }

    @Override
    public Object parseInstallationPart(File inputFile) throws Exception
    {
        return parseInstallations(readLines(inputFile));
    }


    private List<InstallationCandidate> parseInstallations(List<String> lines)
    {
        List<InstallationCandidate> installations = new ArrayList<>();
        int start = readInteger(lines);
        lines.subList(1, start + 1)
                .stream()
                .filter(line -> line.contains(","))
                .forEach(line ->
                {
                    String[] parts = splitInstallationLine(line);
                    installations.add(new InstallationCandidate(parts[0], parts[1]));
                });
        return installations;
    }

    private DependencyTree parseDependencies(List<String> lines)
    {
        DependencyTree dependencyTree = new DependencyTree();
        int start = readInteger(lines);
        lines.stream()
                .skip(start + 1L)
                .filter(line ->  StringUtils.countCharInString(line, ",") == 3 )
                .forEach(line -> {
                    String[] parts = splitDependencyLine(line);
                    dependencyTree.put(parts[0] + "." + parts[1] + ","
                                    + parts[2] + "." + parts[3],
                            new InstallationCandidate(parts[2], parts[3]));
                });
        return dependencyTree;
    }

    private int readInteger(List<String> lines)
    {
        int start;
        try {
            start = Integer.parseInt(lines.get(0));
        }
        catch (NumberFormatException numberFormatException)
        {
            logger.error("input installation package format error.");
            throw new IllegalInputFormatException("Input format is incorrect.  " +
                    "The line must be an integer.");
        }
        return start;
    }

    private String[] splitDependencyLine(String line)
    {
        String[] parts = line.split(",");
        if (parts.length != 4)
        {
            String message = "input format error.  should be like: a,1,b,2, but actually " + line;
            logger.error(message);
            throw new IllegalInputFormatException(message);
        }
        return parts;
    }

    private String[] splitInstallationLine(String line)
    {
        String[] parts = line.split(",");
        if (parts.length != 2)
        {
            String message = "input format error.  should be like: a,1, but actually " + line;
            logger.error(message);
            throw new IllegalInputFormatException(message);
        }
        return parts;
    }

    private List<String> readLines(File file) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while(line != null)
        {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }
}
