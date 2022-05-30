package com.example.wanghan.util;

import java.io.File;

public interface DataReader {

    Object parseInstallationPart(File file) throws Exception;

    Object parseInstallationPart(String source) throws Exception;

    Object parseDependencyPart(File file) throws Exception;

    Object parseDependencyPart(String source) throws Exception;
}
