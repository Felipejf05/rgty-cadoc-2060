package com.rgty.cadoc2060.controller;

import com.rgty.cadoc2060.dto.CadocFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Regulatory Cadoc 2060")
@RequestMapping("/v1")
public interface FileController {

    @Operation(summary = "Upload de arquivo regulatório 2060")
    @PostMapping(value = "/upload-cadoc-file",
                consumes = MULTIPART_FORM_DATA_VALUE,
                produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200",
                description = "Arquivo recebido com sucesso",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CadocFileResponseDTO.class)))
    ResponseEntity<CadocFileResponseDTO> uploadCadocFile(@RequestPart final MultipartFile file);

    @Operation(summary = "Geração do link para download do arquivo Cadoc 2060 pedido pelo usuário")
    @GetMapping(value = "/download-cadoc-file", produces = APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponse(responseCode = "200",
                description = "Arquivo enviado com sucesso",
                content = @Content(mediaType = APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = InputStreamResource.class)))
    ResponseEntity<InputStreamResource>downloadFile(@RequestParam final String fileName);
}
