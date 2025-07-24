package com.rgty.cadoc2060.common.helper;

import org.springframework.stereotype.Component;

@Component
public class LogGenerator {

    public String logMsg(String fileName, String message) {
        return String.format("Arquivo: %s : %s", fileName, message);
    }
}
