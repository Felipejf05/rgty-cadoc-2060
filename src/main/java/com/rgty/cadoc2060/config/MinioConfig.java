package com.rgty.cadoc2060.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.signer.AwsS3V4Signer;
import software.amazon.awssdk.core.client.config.SdkAdvancedClientOption;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
  private final MinioProperties ioProperties;

  @Bean
  public S3Client ioClient() throws URISyntaxException {
    final var credentials = AwsBasicCredentials.create(ioProperties.getAccessKey(), ioProperties.getSecretKey());

    return S3Client.builder()
        .endpointOverride(new URI(ioProperties.getHost()))
        .region(Region.US_EAST_1)
        .serviceConfiguration(e -> e.pathStyleAccessEnabled(true))
        .overrideConfiguration(c -> c.putAdvancedOption(SdkAdvancedClientOption.SIGNER, AwsS3V4Signer.create()))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }

  @Bean
  public S3Presigner ioPresigner() throws URISyntaxException {
    final var credentials = AwsBasicCredentials.create(ioProperties.getAccessKey(), ioProperties.getSecretKey());

    return S3Presigner.builder()
        .endpointOverride(new URI(ioProperties.getHost()))
        .region(Region.US_EAST_1)
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }
}