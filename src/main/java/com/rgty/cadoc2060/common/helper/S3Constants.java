package com.rgty.cadoc2060.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class S3Constants {
    public static final String S3_ROOT_FOLDER = "fintech001/rgty/cadoc/2060";
    public static final String S3_IN = "/in";
    public static final String S3_PROCESSED = "/processed";
    public static final String SLASH = "/";
    public static final String S3_IN_PATH = S3_ROOT_FOLDER + S3_IN + SLASH;
    public static final String S3_PROCESSED_PATH = S3_ROOT_FOLDER + S3_PROCESSED + SLASH;

    public static final int MB = 1024 * 1024;
    public static final int TEN_MB = 10 * MB;
}
