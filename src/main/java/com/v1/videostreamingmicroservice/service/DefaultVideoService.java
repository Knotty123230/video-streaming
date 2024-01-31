package com.v1.videostreamingmicroservice.service;


import com.v1.videostreamingmicroservice.binarystorage.MinioStorageService;
import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import com.v1.videostreamingmicroservice.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * The type Default video service.
 */
@Slf4j
@Service
public class DefaultVideoService implements VideoService {

    private final MinioStorageService storageService;
    private final Sender sender;


    /**
     * Instantiates a new Default video service.
     *
     * @param storageService the storage service
     * @param sender         the sender
     */
    public DefaultVideoService(MinioStorageService storageService, Sender sender) {
        this.storageService = storageService;
        this.sender = sender;
    }

    @Override
    public FileMetadataDTO save(MultipartFile video, String name, String description) {
        try {
            UUID fileUuid = UUID.randomUUID();
            FileMetadataDTO metadata = FileMetadataDTO.builder()
                    .id(fileUuid.toString())
                    .size(video.getSize())
                    .header(name)
                    .description(description)
                    .name(video.getName())
                    .httpContentType(video.getContentType())
                    .build();
            FileMetadataDTO send = sender.send(metadata, metadata.id());
            log.info("metadata send to kafka {}", send);
            storageService.save(video, fileUuid);
            return send;
        } catch (Exception ex) {
            log.error("Exception occurred when trying to save the file", ex);
            throw new StorageException(ex);
        }
    }
}
