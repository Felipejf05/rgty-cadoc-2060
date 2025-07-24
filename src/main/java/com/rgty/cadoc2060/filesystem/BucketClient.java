package com.rgty.cadoc2060.filesystem;

import com.rgty.cadoc2060.domain.CadocCompany;
import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.FileReadingException;
import com.rgty.cadoc2060.exception.InternalServerException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public interface BucketClient {

    void uploadFileFromRequest(final MultipartFile multipartFile);

    InputStreamResource downloadFile(final String fileName);

    InputStream readFile(String objectKey)
            throws FileReadingException, InternalServerException;

    InputStream readFileStream(final String objectKey)
            throws FileReadingException, InternalServerException;

    List<CadocFile> getCadocFiles(final String filePath, Collection<CadocCompany> cadocCompanies);

    void deleteFileFrom(final String srcFolder, final String fileName);

    void copyFileTo(final String srcFolder, final String FileName, final String targetFolder);

    void moveFile(final String srcFolder, final String fileName, final String targetFolder);
}
