package com.rgty.cadoc2060.controller.impl;

import com.rgty.cadoc2060.controller.FileController;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.dto.CadocFileResponseDTO;
import com.rgty.cadoc2060.service.CadocFileService;
import com.rgty.cadoc2060.service.FileActionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {
    private final CadocFileService cadocFileService;
    private final FileActionsService fileActionsService;

    @Override
    public ResponseEntity<CadocFileResponseDTO> uploadCadocFile(MultipartFile file) {
        CadocFile cadocFile = fileActionsService.uploadFileFromRequest(file);
        final var responseDTO = new CadocFileResponseDTO(cadocFile);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(final String fileName) {
        final var cadocFile = fileActionsService.downloadFile(fileName);
        final var httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return new ResponseEntity<>(cadocFile, httpHeaders, HttpStatus.OK);
    }
}
