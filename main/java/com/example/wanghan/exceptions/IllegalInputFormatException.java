package com.example.wanghan.exceptions;

import java.util.Map;

public class IllegalInputFormatException extends IllegalArgumentException{
    public int reasonCode = 0;
    public Map<String, Object> responseMap;

    public IllegalInputFormatException(String message, int reasonCode, Map<String, Object> responseMap) {
        super(message);
        this.reasonCode = reasonCode;
        this.responseMap = responseMap;
    }

    public IllegalInputFormatException(String message)
    {
        super((message));
    }

}
