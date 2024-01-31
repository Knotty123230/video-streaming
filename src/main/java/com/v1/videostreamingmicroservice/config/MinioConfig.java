package com.v1.videostreamingmicroservice.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * The type Minio config.
 */
@Configuration
@Slf4j
public class MinioConfig {

    /**
     * The constant COMMON_BUCKET_NAME.
     */
    public static final String COMMON_BUCKET_NAME = "common";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    /**
     * Minio client minio client.
     *
     * @return the minio client
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();
        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(COMMON_BUCKET_NAME).build())) {
                client.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(COMMON_BUCKET_NAME)
                                .build()
                );
            }
        } catch (ErrorResponseException | IOException | XmlParserException | NoSuchAlgorithmException |
                 ServerException | InvalidKeyException | InvalidResponseException | InternalException |
                 InsufficientDataException e) {
            log.error("Configuration exception {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return client;
    }
}
