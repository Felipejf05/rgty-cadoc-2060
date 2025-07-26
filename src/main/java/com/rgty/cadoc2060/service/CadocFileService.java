package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.CadocFileNotFoundException;
import com.rgty.cadoc2060.repository.CadocFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CadocFileService {

    private final CadocFileRepository cadocFileRepository;

    public CadocFile addFile(final String fileName, final Long statusId, final int retryCount,
                             final LocalDateTime inputTime,final Long fileSize) {
        log.info("Iniciando a criação do arquivo: {}", fileName);

        CadocFile cadocFile = CadocFile.builder()
                .name(fileName)
                .statusId(statusId)
                .retryCount(retryCount)
                .inputTime(LocalDateTime.now())
                .fileSize(fileSize)
                .build();
        return cadocFileRepository.save(cadocFile);
    }

    public List<CadocFile> getFiles() {
        log.info("Listando os arquivos Cadoc2060");
        return cadocFileRepository.findAll();
    }

    public CadocFile getCadocById(Long id) {
        log.info("Buscando CadocFile pelo Id: {}", id);
        return cadocFileRepository.findById(id).
                orElseThrow(() -> new CadocFileNotFoundException("Cadoc com o id: {}", id));
    }

    public boolean existsFile(final String fileName) {
        return cadocFileRepository.existsByName(fileName);
    }

}
