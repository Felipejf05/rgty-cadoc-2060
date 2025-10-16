package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.CadocFileNotFoundException;
import com.rgty.cadoc2060.repository.CadocFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.rgty.cadoc2060.mocks.MockCadocData.buildCadocFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CadocFileService Tests")
class CadocFileServiceTest {

    @Mock
    private CadocFileRepository cadocFileRepository;

    @InjectMocks
    private CadocFileService cadocFileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadocFileService = new CadocFileService(cadocFileRepository);
    }

    @Test
    @DisplayName("Should add file successfully")
    void shouldAddFileSuccessfully() {
        final var cadocFileMock = buildCadocFile();

        when(cadocFileRepository.save(any(CadocFile.class)))
                .thenReturn(cadocFileMock);

        CadocFile result = cadocFileService.addFile(
                cadocFileMock.getName(),
                cadocFileMock.getStatusId(),
                cadocFileMock.getRetryCount(),
                cadocFileMock.getInputTime(),
                cadocFileMock.getFileSize()
        );

        verify(cadocFileRepository, times(1)).save(any(CadocFile.class));
        assertNotNull(result);
        assertEquals(cadocFileMock, result);
    }

    @Test
    @DisplayName("Should add file with null ID")
    void shouldAddFileWithNullId() {
        final var cadocFileMock = buildCadocFile();

        CadocFile newFile = new CadocFile();
        newFile.setId(null);

        CadocFile savedFile = new CadocFile();
        savedFile.setId(3L);

        when(cadocFileRepository.save(any(CadocFile.class))).thenReturn(savedFile);

        CadocFile result = cadocFileService.addFile(
                cadocFileMock.getName(),
                cadocFileMock.getStatusId(),
                cadocFileMock.getRetryCount(),
                cadocFileMock.getInputTime(),
                cadocFileMock.getFileSize()
        );

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        verify(cadocFileRepository, times(1)).save(any(CadocFile.class));


    }

    @Test
    @DisplayName("Should get all files successfully")
    void shouldGetAllFilesSuccessfully() {
        final var cadocFile1 = buildCadocFile();

        final var cadocFile2 = CadocFile.builder()
                .id(2L)
                .name("OutroArquivo.xml")
                .statusId(1L)
                .retryCount(0)
                .fileSize(456L)
                .inputTime(LocalDateTime.now())
                .build();

        List<CadocFile> expectedFiles = List.of(cadocFile1, cadocFile2);
        when(cadocFileRepository.findAll()).thenReturn(expectedFiles);

        List<CadocFile> result = cadocFileService.getFiles();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(cadocFile1, cadocFile2);
        verify(cadocFileRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no files exist")
    void shouldReturnEmptyListWhenNoFilesExist() {

        when(cadocFileRepository.findAll()).thenReturn(Collections.emptyList());

        List<CadocFile> result = cadocFileService.getFiles();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(cadocFileRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get CadocFile by ID successfully")
    void shouldGetCadocFileByIdSuccessfully() {
        final var cadocFileMock = buildCadocFile();

        Long id = 1L;

        when(cadocFileRepository.findById(id)).thenReturn(Optional.of(cadocFileMock));

        CadocFile result = cadocFileService.getCadocById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(cadocFileRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw CadocFileNotFoundException when ID not found")
    void shouldThrowCadocFileNotFoundExceptionWhenIdNotFound() {

        Long id = 999L;
        when(cadocFileRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
                .isInstanceOf(CadocFileNotFoundException.class)
                .hasMessageContaining("Cadoc com o id: {}");

        verify(cadocFileRepository, times(1)).findById(id);
    }


    @Test
    @DisplayName("Should throw CadocFileNotFoundException when searching with null ID")
    void shouldThrowCadocFileNotFoundExceptionWhenSearchingWithNullId() {
        Long id = null;

        when(cadocFileRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
                .isInstanceOf(CadocFileNotFoundException.class)
                .hasMessageContaining("Cadoc com o id: {}");
        verify(cadocFileRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should handle repository exception during save")
    void shouldHandleRepositoryExceptionDuringSave() {
        final var cadocFileMock = buildCadocFile();

        when(cadocFileRepository.save(any(CadocFile.class)))
                .thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> cadocFileService.addFile(
                cadocFileMock.getName(),
                cadocFileMock.getStatusId(),
                cadocFileMock.getRetryCount(),
                cadocFileMock.getInputTime(),
                cadocFileMock.getFileSize()
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");

        verify(cadocFileRepository, times(1)).save(any(CadocFile.class));

    }

    @Test
    @DisplayName("Should handle repository exception during findAll")
    void shouldHandleRepositoryExceptionDuringFindAll() {

        when(cadocFileRepository.findAll())
                .thenThrow(new RuntimeException("Database connection error"));

        assertThatThrownBy(() -> cadocFileService.getFiles())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database connection error");

        verify(cadocFileRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should handle repository exception during findById")
    void shouldHandleRepositoryExceptionDuringFindById() {

        Long id = 1L;
        when(cadocFileRepository.findById(anyLong()))
                .thenThrow(new RuntimeException("Database timeout"));

        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database timeout");

        verify(cadocFileRepository, times(1)).findById(id);
    }
}