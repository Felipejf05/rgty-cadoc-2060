//package com.rgty.cadoc2060.service;
//
//import com.rgty.cadoc2060.domain.CadocFile;
//import com.rgty.cadoc2060.exception.CadocFileNotFoundException;
//import com.rgty.cadoc2060.repository.CadocFileRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("CadocFileService Tests")
//class CadocFileServiceTest {
//
//    @Mock
//    private CadocFileRepository cadocFileRepository;
//
//    @InjectMocks
//    private CadocFileService cadocFileService;
//
//    private CadocFile cadocFile1;
//    private CadocFile cadocFile2;
//
//    @BeforeEach
//    void setUp() {
//        cadocFile1 = new CadocFile();
//        cadocFile1.setId(1L);
//
//        cadocFile2 = new CadocFile();
//        cadocFile2.setId(2L);
//    }
//
//    @Test
//    @DisplayName("Should add file successfully")
//    void shouldAddFileSuccessfully() {
//
//        when(cadocFileRepository.save(any(CadocFile.class))).thenReturn(cadocFile1);
//
//        CadocFile result = cadocFileService.addFile(cadocFile1);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(1L);
//        verify(cadocFileRepository, times(1)).save(cadocFile1);
//    }
//
//    @Test
//    @DisplayName("Should add file with null ID")
//    void shouldAddFileWithNullId() {
//
//        CadocFile newFile = new CadocFile();
//        newFile.setId(null);
//
//        CadocFile savedFile = new CadocFile();
//        savedFile.setId(3L);
//
//        when(cadocFileRepository.save(any(CadocFile.class))).thenReturn(savedFile);
//
//        CadocFile result = cadocFileService.addFile(newFile);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(3L);
//        verify(cadocFileRepository, times(1)).save(newFile);
//
//
//    }
//
//    @Test
//    @DisplayName("Should get all files successfully")
//    void shouldGetAllFilesSuccessfully() {
//
//        List<CadocFile> expectedFiles = Arrays.asList(cadocFile1, cadocFile2);
//        when(cadocFileRepository.findAll()).thenReturn(expectedFiles);
//
//        List<CadocFile> result = cadocFileService.getFiles();
//
//        assertThat(result).isNotNull();
//        assertThat(result).hasSize(2);
//        assertThat(result).containsExactly(cadocFile1, cadocFile2);
//        verify(cadocFileRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Should return empty list when no files exist")
//    void shouldReturnEmptyListWhenNoFilesExist() {
//
//        when(cadocFileRepository.findAll()).thenReturn(Collections.emptyList());
//
//        List<CadocFile> result = cadocFileService.getFiles();
//
//        assertThat(result).isNotNull();
//        assertThat(result).isEmpty();
//        verify(cadocFileRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Should get CadocFile by ID successfully")
//    void shouldGetCadocFileByIdSuccessfully() {
//
//        Long id = 1L;
//
//        when(cadocFileRepository.findById(id)).thenReturn(Optional.of(cadocFile1));
//
//        CadocFile result = cadocFileService.getCadocById(id);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(id);
//        verify(cadocFileRepository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Should throw CadocFileNotFoundException when ID not found")
//    void shouldThrowCadocFileNotFoundExceptionWhenIdNotFound() {
//
//        Long id = 999L;
//        when(cadocFileRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
//                .isInstanceOf(CadocFileNotFoundException.class)
//                .hasMessageContaining("Cadoc com o id: {}");
//
//        verify(cadocFileRepository, times(1)).findById(id);
//    }
//
//
//    @Test
//    @DisplayName("Should throw CadocFileNotFoundException when searching with null ID")
//    void shouldThrowCadocFileNotFoundExceptionWhenSearchingWithNullId() {
//        Long id = null;
//
//        when(cadocFileRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
//        .isInstanceOf(CadocFileNotFoundException.class)
//                .hasMessageContaining("Cadoc com o id: {}");
//        verify(cadocFileRepository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Should handle repository exception during save")
//    void shouldHandleRepositoryExceptionDuringSave(){
//
//        when(cadocFileRepository.save(any(CadocFile.class)))
//                .thenThrow(new RuntimeException("Database error"));
//
//        assertThatThrownBy(() -> cadocFileService.addFile(cadocFile1))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("Database error");
//
//        verify(cadocFileRepository, times(1)).save(cadocFile1);
//
//    }
//
//    @Test
//    @DisplayName("Should handle repository exception during findAll")
//    void shouldHandleRepositoryExceptionDuringFindAll() {
//
//        when(cadocFileRepository.findAll())
//                .thenThrow(new RuntimeException("Database connection error"));
//
//        assertThatThrownBy(() -> cadocFileService.getFiles())
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("Database connection error");
//
//        verify(cadocFileRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Should handle repository exception during findById")
//    void shouldHandleRepositoryExceptionDuringFindById() {
//
//        Long id = 1L;
//        when(cadocFileRepository.findById(anyLong()))
//                .thenThrow(new RuntimeException("Database timeout"));
//
//        assertThatThrownBy(() -> cadocFileService.getCadocById(id))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("Database timeout");
//
//        verify(cadocFileRepository, times(1)).findById(id);
//    }
//}