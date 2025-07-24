package com.rgty.cadoc2060.filesystem;

import com.rgty.cadoc2060.domain.CadocCompany;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.FileReadingException;
import com.rgty.cadoc2060.exception.FileValidationException;
import com.rgty.cadoc2060.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public final class S3ClientFileSystem implements BucketClient {
public static final String ERRO_AO_LER_ARQUIVO = "Erro ao ler arquivo";
private final S3Client awsClient;

    @Value("${cadoc2060.upload-dir}")
    private String uploadDir;

    @Override
    public void uploadFileFromRequest(MultipartFile file) {
       try {
           var filePath = Paths.get(uploadDir, file.getOriginalFilename());
           Files.createDirectories(filePath.getParent());
           file.transferTo(filePath);
       }catch(IOException e){
            throw new FileValidationException("Erro ao salvar o arquivo localmente: " + e.getMessage());
        }
    }

    @Override
    public InputStreamResource downloadFile(String fileName) {
        return null;
    }

    @Override
    public InputStream readFile(String objectKey) throws FileReadingException, InternalServerException {
        return null;
    }

    @Override
    public InputStream readFileStream(String objectKey) throws FileReadingException, InternalServerException {
        return null;
    }

    @Override
    public List<CadocFile> getCadocFiles(String filePath, Collection<CadocCompany> cadocCompanies) {
        return List.of();
    }

    @Override
    public void deleteFileFrom(String srcFolder, String fileName) {

    }

    @Override
    public void copyFileTo(String srcFolder, String FileName, String targetFolder) {

    }

    @Override
    public void moveFile(String srcFolder, String fileName, String targetFolder) {

    }
}
