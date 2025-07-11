package com.rgty.cadoc2060.common;

import lombok.Getter;

@Getter
public final class CadocConstants {

    private CadocConstants() {

    }

    public static final String INBOX_FOLDER = "inbox";
    public static final String VALIDATED_FOLDER = "validated";
    public static final String ERROR_FOLDER = "'error";

    public static final String CSV_SEPARATOR = ";";
    public static final String FILE_NAME = "cadoc2060.csv";
}
