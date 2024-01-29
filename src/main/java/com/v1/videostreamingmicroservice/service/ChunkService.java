package com.v1.videostreamingmicroservice.service;


import com.v1.videostreamingmicroservice.binarystorage.MinioStorageService;
import com.v1.videostreamingmicroservice.dto.ChunkWithMetadata;
import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import com.v1.videostreamingmicroservice.exception.StorageException;
import com.v1.videostreamingmicroservice.util.Range;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChunkService {
    private final MinioStorageService storageService;
    private final RestTemplate restTemplate;

    private byte[] readChunk(UUID uuid, Range range, long fileSize) {
        long startPosition = range.getRangeStart();
        long endPosition = range.getRangeEnd(fileSize);
        int chunkSize = (int) (endPosition - startPosition + 1);
        log.info("chunk size : {} Chunk start position {} Chunk end position {}", chunkSize, startPosition, endPosition);
        try (InputStream inputStream = storageService.getInputStream(uuid, startPosition, chunkSize)) {
            return inputStream.readAllBytes();
        } catch (Exception exception) {
            log.error("Exception occurred when trying to read file with ID = {}", uuid);
            throw new StorageException(exception);
        }
    }

    public ChunkWithMetadata fetchChunk(UUID uuid, Range range) {
        FileMetadataDTO fileMetadata = restTemplate.getForObject("http://localhost:8080/file-metadata-storage/api/" + uuid, FileMetadataDTO.class);
        Optional<FileMetadataDTO> optionalFileMetadataDTO = Optional.ofNullable(fileMetadata);
        return new ChunkWithMetadata(fileMetadata, readChunk(uuid, range, optionalFileMetadataDTO.orElseThrow().size()));
    }
}
