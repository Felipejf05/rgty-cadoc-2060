package com.rgty.cadoc2060.common;

import lombok.Getter;

@Getter
public final class CadocConstants {

    private CadocConstants() {

    }

    public static final String INBOX_FOLDER = "inbox";
    public static final String VALIDATED_FOLDER = "validated";
    public static final String ERROR_FOLDER = "error";

    public static final String CSV_SEPARATOR = ";";
    public static final String FILE_NAME = "cadoc2060.csv";

    public static final int GROUPS_LENGTH = 4;
    public static final String EXPECTED_PREFIX = "cadoc-2060";
    public static final String EXPECTED_EXTENSION = "xml";
    public static final String UNDEFINED_FORMAT = "não está no formato definido";

}
