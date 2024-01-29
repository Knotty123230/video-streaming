package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    FileMetadataDTO save(MultipartFile video, String name, String description);

}
