package com.rgty.cadoc2060.common.helper;

import org.springframework.stereotype.Component;

@Component
public class LogGenerator {

    public String logMsg(String fileName, String message) {
        return String.format("Arquivo: %s : %s", fileName, message);
    }

    public String errorMsg(final String fileName, final String message, final Throwable e) {
        var logMessage = "[ERRO] " + messageBody(fileName, message);

        if (e != null) {
            logMessage += " | Exception Message: " + e.getMessage();
        }

        return logMessage;
    }

    private static String messageBody(final String fileName, final String message) {
        return " Key: " + fileName + " | Message: " + message;
    }
}