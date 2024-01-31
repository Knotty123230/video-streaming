package com.v1.videostreamingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The type Video streaming microservice application.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class VideoStreamingMicroserviceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VideoStreamingMicroserviceApplication.class, args);
    }

}
