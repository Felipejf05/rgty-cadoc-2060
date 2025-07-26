package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.common.helper.LogGenerator;
import com.rgty.cadoc2060.common.singleton.CadocStatusSingleton;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.FileValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static com.rgty.cadoc2060.common.helper.Messages.FILE_ALREADY_EXISTS;
import static com.rgty.cadoc2060.common.helper.Messages.FILE_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileActionsService {

    private final LogGenerator logGenerator;
    private final CadocFileService cadocFileService;
//    private final S3ClientFileSystem s3ClientFileSystem;
    private final CadocStatusSingleton cadocStatusSingleton;
    private final ValidateFileNameUseCase validateFileNameUseCase;

    @Value("${cadoc2060.upload-dir}")
    private String uploadDir;

    public CadocFile uploadFileFromRequest(final MultipartFile file) {
        log.info(logGenerator.logMsg(file.getName(), "Iniciando o upload do arquivo..."));

        final var fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            throw new FileValidationException("Nome do arquivo está vazio ou nulo");
        }

        var isValid = validateFileNameUseCase.validateFileName(fileName);
        if (!isValid) {
            throw new FileValidationException("Erro no nome do arquivo " + fileName);
        }

        final var hasFile = cadocFileService.existsFile(fileName);
        if (hasFile) {
            log.info(logGenerator.logMsg(fileName, FILE_ALREADY_EXISTS));
            throw new FileValidationException(FILE_ALREADY_EXISTS);
        }

//        s3ClientFileSystem.uploadFileFromRequest(file);
        final var cadocFile = persistFileInformation(file);

        log.info(logGenerator.logMsg(fileName, "Arquivo enviado"));

        return cadocFile;
    }

    private CadocFile persistFileInformation(final MultipartFile file) {
        final var fileName = file.getOriginalFilename();
        final var statusId = cadocStatusSingleton.getReceived().getId();
        final var RETRY_COUNT = 0;
        final var inputTime = LocalDateTime.now();
        final var fileSize = file.getSize();

        return cadocFileService.addFile(fileName, statusId, RETRY_COUNT, inputTime, fileSize);
    }

    public InputStreamResource downloadFile(final String fileName) {

        log.info("Recebido para download: '{}'", fileName);

        final var hasFile = cadocFileService.existsFile(fileName);

        if (!hasFile) {
            log.info(logGenerator.logMsg(fileName, FILE_NOT_FOUND));
            throw new FileValidationException(FILE_NOT_FOUND);
        }

        log.info(logGenerator.logMsg(fileName, "Iniciando o download do arquivo..."));

        try {
            Path path = Paths.get(uploadDir, fileName);
            InputStream inputStream = Files.newInputStream(path);

            return new InputStreamResource(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo local: " + fileName, e);
        }

    }
}
