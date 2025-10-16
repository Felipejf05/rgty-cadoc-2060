package com.rgty.cadoc2060.mocks;

import com.rgty.cadoc2060.domain.CadocFile;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class MockCadocData {
public static String FILE_NAME = "testFile";
public static String FILE_WITH_EXTENSION = "testeFile.txt";
public static String CONTENT_TYPE = "text/plain";
public static final String CADOC_FILE_NAME = "cadoc-2060_FINTECH001_20250714_120000.xml";

public static CadocFile buildCadocFile(){
return CadocFile.builder()
        .id(1L)
        .name("File.xml")
        .statusId(1L)
        .retryCount(0)
        .fileSize(123L)
        .build();

}
}
