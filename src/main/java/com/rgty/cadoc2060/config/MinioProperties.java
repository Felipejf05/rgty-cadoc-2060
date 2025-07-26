package com.rgty.cadoc2060.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MinioProperties {
  private final String host;
  private final String accessKey;
  private final String secretKey;
  private final String region;
  private final String bucket;

  public MinioProperties(
      @Value("${minio.bucket.host}") final String host,
      @Value("${minio.bucket.accessKey}") final String accessKey,
      @Value("${minio.bucket.secretKey}") final String secretKey,
      @Value("${minio.bucket.region}") final String region,
      @Value("${minio.bucket.bucketName}") final String bucket) {
    this.host = host;
    this.accessKey = accessKey;
    this.secretKey = secretKey;
    this.region = region;
    this.bucket = bucket;
  }
}