package com.example.wanghan.util;

import com.example.wanghan.model.InstallationCandidate;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.logging.LogManager;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class DataTxtReaderTest {

    @Test
    public void test_parseInstallationsFromFile() throws Exception
    {
        String filePath = "src/main/resources/input/input009.txt";

        DataTxtReader dataReader = new DataTxtReader();

        List<InstallationCandidate> installations = (List<InstallationCandidate>) dataReader.parseInstallationPart(new File(filePath));

        assertNotNull(installations);
        assertEquals(installations.size(), 3);

        InstallationCandidate installationCandidate1 = installations.get(0);
        assertNotNull(installationCandidate1);
        assertEquals(installationCandidate1.getKey(), "A.2");

        InstallationCandidate installationCandidate2 = installations.get(1);
        assertNotNull(installationCandidate2);
        assertEquals(installationCandidate2.getKey(), "B.2");

        InstallationCandidate installationCandidate3 = installations.get(2);
        assertNotNull(installationCandidate3);
        assertEquals(installationCandidate3.getKey(), "G.1");
    }
}
