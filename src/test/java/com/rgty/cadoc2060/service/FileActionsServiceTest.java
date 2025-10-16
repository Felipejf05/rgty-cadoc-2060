package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.common.helper.LogGenerator;
import com.rgty.cadoc2060.common.singleton.CadocStatusSingleton;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.domain.CadocStatus;
import com.rgty.cadoc2060.exception.FileValidationException;
import com.rgty.cadoc2060.filesystem.BucketClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mock.web.MockMultipartFile;

import static com.rgty.cadoc2060.mocks.MockCadocData.CADOC_FILE_NAME;
import static com.rgty.cadoc2060.mocks.MockCadocData.CONTENT_TYPE;
import static com.rgty.cadoc2060.mocks.MockCadocData.FILE_NAME;
import static com.rgty.cadoc2060.mocks.MockCadocData.FILE_WITH_EXTENSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileActionsServiceTest {

    @Mock
    private CadocFileService cadocFileService;

    @Mock
    private BucketClient s3ClientFileSystem;

    @Mock
    private CadocStatusSingleton cadocStatusSingleton;

    @Mock
    private ValidateFileNameUseCase validateFileNameUseCase;

    private FileActionsService fileActionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LogGenerator logGenerator = new LogGenerator();
        fileActionsService = new FileActionsService(logGenerator, cadocFileService, s3ClientFileSystem, cadocStatusSingleton, validateFileNameUseCase);
    }

    @Test
    void whenUploadFileRequestThenRequestSuccessful() {

        final var file = new MockMultipartFile(CADOC_FILE_NAME, CADOC_FILE_NAME.concat(".xml"), CONTENT_TYPE, "FileContent".getBytes());
        final var cadocFileMock = new CadocFile();

        when(cadocFileService.existsFile(any())).thenReturn(false);
        when(cadocFileService.addFile(anyString(), anyLong(), anyInt(), any(), anyLong())).thenReturn(cadocFileMock);
        when(cadocStatusSingleton.getReceived()).thenReturn(new CadocStatus(1L, "RECEIVED"));
        when(validateFileNameUseCase.validateFileName(any())).thenReturn(true);

        final var result = fileActionsService.uploadFileFromRequest(file);

        verify(s3ClientFileSystem, times(1)).uploadFileFromRequest(file);
        verify(cadocFileService, times(1)).addFile(any(), anyLong(), anyInt(), any(), anyLong());
        verify(cadocStatusSingleton, times(1)).getReceived();
        verify(cadocFileService, times(1)).existsFile(any());
        verify(validateFileNameUseCase, times(1)).validateFileName(any());

        assertEquals(cadocFileMock, result);
    }

    @Test
    void testUploadFileFromRequestWithInvalidFile() {
        final var file = new MockMultipartFile(FILE_NAME, FILE_WITH_EXTENSION, CONTENT_TYPE, "FileContent".getBytes());

        when(validateFileNameUseCase.validateFileName(any())).thenReturn(false);
        when(cadocFileService.existsFile(any())).thenReturn(false);
        when(cadocStatusSingleton.getReceived()).thenReturn(new CadocStatus(1L, "RECEIVED"));

        assertThrows(FileValidationException.class, () -> fileActionsService.uploadFileFromRequest(file));
    }

    @Test
    void whenUploadFileFromRequestWithDuplicatedNameThenThrowFileValidationException() {
        final var file = new MockMultipartFile(FILE_NAME, FILE_WITH_EXTENSION, CONTENT_TYPE, "FileContent".getBytes());

        when(validateFileNameUseCase.validateFileName(any())).thenReturn(true);
        when(cadocFileService.existsFile(any())).thenReturn(true);
        assertThrows(FileValidationException.class, () -> fileActionsService.uploadFileFromRequest(file));
    }

    @Test
    void whenDownloadFileReturnResponseEntity() {
        final var awsResponse = mock(InputStreamResource.class);

        when(cadocFileService.existsFile(FILE_WITH_EXTENSION)).thenReturn(true);
        when(s3ClientFileSystem.downloadFile(FILE_WITH_EXTENSION)).thenReturn(awsResponse);

        final var result = fileActionsService.downloadFile(FILE_WITH_EXTENSION);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(awsResponse.getClass(), result.getClass());

    }

    @Test
    void whenDownloadFileThrowInternalServerException() {
        when(cadocFileService.existsFile(any())).thenReturn(false);
        Assertions.assertThrows(FileValidationException.class, () -> fileActionsService.downloadFile(FILE_WITH_EXTENSION));
    }

    @Test
    void whenCheckIfFileExistsReturnTrue() {
        when(cadocFileService.existsFile(FILE_WITH_EXTENSION)).thenReturn(true);
        when(validateFileNameUseCase.validateFileName(FILE_WITH_EXTENSION)).thenReturn(true);

        final var result = fileActionsService.checkIfFileExists(FILE_WITH_EXTENSION);

        Assertions.assertTrue(result);
    }
}