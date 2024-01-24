package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.entity.FileMetadataEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    FileMetadataEntity save(MultipartFile video, String name, String description);

}
