package com.example.wanghan.util;

import com.example.wanghan.model.InstallationCandidate;
import org.testng.annotations.Test;


import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;


public class DependencyTreeTest
{
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
    public void test_getChildren()
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

        assertTrue(testTree.isRoot());

        List<DependencyTree> children = new ArrayList<>();
        testTree.getChildren("a.1", testTree, children);
        assertNotNull(children);
        assertEquals(children.size(), 2, "Should be totally 2 children");
        assertEquals(children.get(0).getKey(), "b.2");
        assertEquals(children.get(1).getKey(), "b.3");

        children = new ArrayList<>();
        testTree.getChildren("b.2", testTree, children);
        assertNotNull(children);
        assertEquals(children.size(), 3, "Should be totally 3 children");
        assertEquals(children.get(0).getKey(), "c.3");
        assertEquals(children.get(1).getKey(), "c.4");
        assertEquals(children.get(2).getKey(), "f.1");

        children = new ArrayList<>();
        testTree.getChildren("f.1", testTree, children);
        assertNotNull(children);
        assertEquals(children.size(), 0, "Should be no child");



    }


}
