package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Video service.
 */
public interface VideoService {

    /**
     * Save file metadata dto.
     *
     * @param video       the video
     * @param name        the name
     * @param description the description
     * @return the file metadata dto
     */
    FileMetadataDTO save(MultipartFile video, String name, String description);

}
