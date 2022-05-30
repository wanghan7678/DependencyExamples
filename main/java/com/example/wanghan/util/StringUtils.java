package com.example.wanghan.util;

public class StringUtils {

    public static int countCharInString(String theString, String theChar)
    {
        return theString.length() - theString.replaceAll(theChar,"").length();
    }
}
