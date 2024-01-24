package com.v1.videostreamingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VideoStreamingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoStreamingMicroserviceApplication.class, args);
    }

}
