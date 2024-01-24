package com.v1.videostreamingmicroservice.service;


import com.v1.videostreamingmicroservice.binarystorage.MinioStorageService;
import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import com.v1.videostreamingmicroservice.entity.FileMetadataEntity;
import com.v1.videostreamingmicroservice.exception.StorageException;
import com.v1.videostreamingmicroservice.repository.FileMetadataRelationalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class DefaultVideoService implements VideoService {

    private final MinioStorageService storageService;
    private final Sender sender;

    private final FileMetadataRelationalRepository fileMetadataRelationalRepository;

    public DefaultVideoService(MinioStorageService storageService, Sender sender, FileMetadataRelationalRepository fileMetadataRelationalRepository) {
        this.storageService = storageService;
        this.sender = sender;
        this.fileMetadataRelationalRepository = fileMetadataRelationalRepository;
    }

    @Override
    public FileMetadataEntity save(MultipartFile video, String name, String description) {
        try {
            UUID fileUuid = UUID.randomUUID();
            FileMetadataEntity metadata = FileMetadataEntity.builder()
                    .id(fileUuid.toString())
                    .size(video.getSize())
                    .header(name)
                    .description(description)
                    .name(video.getName())
                    .httpContentType(video.getContentType())
                    .build();
            FileMetadataDTO fileMetadataSendToKafka = sender.send(FileMetadataDTO.builder()
                    .description(description)
                    .id(fileUuid.toString())
                    .header(name)
                    .build());
            log.info("metadata send to kafka {}", fileMetadataSendToKafka);
            storageService.save(video, fileUuid);
            return fileMetadataRelationalRepository.save(metadata);
        } catch (Exception ex) {
            log.error("Exception occurred when trying to save the file", ex);
            throw new StorageException(ex);
        }
    }
}
