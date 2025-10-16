package com.rgty.cadoc2060.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final AwsProperties awsProperties;

    @Bean
    public S3Client awsClient() {
        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .build();
    }
}
