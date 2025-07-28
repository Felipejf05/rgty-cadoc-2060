package com.rgty.cadoc2060.filesystem;

import com.rgty.cadoc2060.common.helper.LogGenerator;
import com.rgty.cadoc2060.config.AwsProperties;
import com.rgty.cadoc2060.domain.CadocCompany;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.FileReadingException;
import com.rgty.cadoc2060.exception.InternalServerException;
import com.rgty.cadoc2060.validator.PatternValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.rgty.cadoc2060.common.helper.Messages.UPLOAD_ERROR;
import static com.rgty.cadoc2060.common.helper.S3Constants.S3_IN_PATH;
import static com.rgty.cadoc2060.common.helper.S3Constants.TEN_MB;

@Slf4j
@Service
@RequiredArgsConstructor
public final class S3ClientFileSystem implements BucketClient {
    public static final String ERRO_AO_LER_ARQUIVO = "Erro ao ler arquivo";
    private final S3Client ioClient;
    private final AwsProperties awsProperties;
    private final LogGenerator logGenerator;
    private final PatternValidator patternValidator;

    @Value("${cadoc2060.upload-dir}")
    private String uploadDir;

    @Override
    public void uploadFileFromRequest(MultipartFile multipartFile) {
        final var fileName = multipartFile.getOriginalFilename();
        final var file = S3_IN_PATH + fileName;
        final var createMultipartUploadRequest = CreateMultipartUploadRequest.builder().bucket(awsProperties.getBucket()).key(file).build();

        final var createResponse = ioClient.createMultipartUpload(createMultipartUploadRequest);
        final var completedParts = new ArrayList<CompletedPart>();

        try {
            byte[] buffer = new byte[TEN_MB];
            int partNumber = 1;

            try (final var inputStream = multipartFile.getInputStream()) {
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    final var uploadRequest = UploadPartRequest.builder()
                            .bucket(awsProperties.getBucket()).key(file)
                            .uploadId(createResponse.uploadId()).partNumber(partNumber)
                            .contentLength((long) bytesRead)
                            .build();

                    final var uploadPartResponse = ioClient
                            .uploadPart(uploadRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(buffer, 0, bytesRead)));

                    final var completedPart = CompletedPart.builder()
                            .partNumber(partNumber)
                            .eTag(uploadPartResponse
                                    .eTag())
                            .build();

                    completedParts.add(completedPart);
                    log.info(logGenerator.logMsg(fileName, "Uploaded 10MB; FilePart: " + partNumber));
                    partNumber += 1;
                }
            }
            final var compRequest = CompleteMultipartUploadRequest.builder()
                    .bucket(awsProperties.getBucket())
                    .key(file)
                    .uploadId(createResponse.uploadId())
                    .multipartUpload(m -> m.parts(completedParts))
                    .build();

            final var completed = ioClient.completeMultipartUpload(compRequest);
            log.info(logGenerator.logMsg(fileName, "File Uploaded in: " + completed.location()));
        } catch (S3Exception | IOException e) {
            log.info(logGenerator.errorMsg(fileName, UPLOAD_ERROR + e.getMessage(), e));
            abortRequest(file, createResponse);

            throw new InternalServerException(UPLOAD_ERROR + e.getMessage());
        }
    }

    private void abortRequest(final String file, final CreateMultipartUploadResponse createResponse){
        final var abortRequest = AbortMultipartUploadRequest.builder()
                .bucket(awsProperties.getBucket())
                .key(file)
                .uploadId(createResponse.uploadId())
                .build();

        ioClient.abortMultipartUpload(abortRequest);
    }

    @Override
    public InputStreamResource downloadFile(String fileName) {
        final var fullpath = S3_IN_PATH + fileName;

        try {
            final var request = GetObjectRequest.builder()
                    .bucket(awsProperties.getBucket())
                    .key(fullpath)
                    .build();

            final var response = ioClient.getObject(request);
            return  new InputStreamResource(response);
        }catch (S3Exception e){
            final var message = logGenerator.errorMsg(fileName, ERRO_AO_LER_ARQUIVO + fullpath + "; " + e.getMessage(),null);
            log.info(message, e);
            throw new InternalServerException(message, e);
        }
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
