package com.example.wanghan.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;



/**
 * this is to register different types of data reader, such as xml, json, yaml...
 */
public class DataReaderRegister
{
    private static final Logger logger = LogManager.getLogger(DataReaderRegister.class);

    private static final ConcurrentHashMap<String, DataReader> readers = new ConcurrentHashMap<>();

    private static DataReader defaultReader = new DataTxtReader();

    public static final void setReader(String format, DataReader reader) {
        String key = format.toLowerCase();
        readers.put(key, reader);
    }

    public static final void removeReader(String format) {
        String key = format.toLowerCase();
        readers.remove(key);
    }

    public static final DataReader getReader(String format) {
        DataReader reader;
        if (format == null) {
            reader = defaultReader;
        } else {
            String key = format.toLowerCase();
            reader = readers.get(key);
        }
        if (reader != null) {
            return reader;
        }
        else
        {
            return defaultReader;
        }
    }



}
