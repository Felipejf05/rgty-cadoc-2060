package com.rgty.cadoc2060.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class AwsProperties {
    private final String region;
    private final String bucket;

    public AwsProperties(
            @Value("${spring.cloud.aws.bucket.region}") final String region,
            @Value("${spring.cloud.aws.bucket.bucketName}") final String bucket) {
        this.region = region;
        this.bucket = bucket;
        log.info("Bucket name: " + bucket);
    }
}
