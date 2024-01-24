package com.v1.videostreamingmicroservice.controller;


import com.v1.videostreamingmicroservice.constants.HttpConstants;
import com.v1.videostreamingmicroservice.dto.ChunkWithMetadata;
import com.v1.videostreamingmicroservice.dto.FileResponseDto;
import com.v1.videostreamingmicroservice.entity.FileMetadataEntity;
import com.v1.videostreamingmicroservice.service.ChunkService;
import com.v1.videostreamingmicroservice.service.RangeCalculator;
import com.v1.videostreamingmicroservice.service.VideoService;
import com.v1.videostreamingmicroservice.util.HeadersUtil;
import com.v1.videostreamingmicroservice.util.Range;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpHeaders.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;
    private final ChunkService chunkService;

    @Value("${photon.streaming.default-chunk-size}")
    public Integer defaultChunkSize;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileResponseDto> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        FileMetadataEntity fileMetadata = videoService.save(file, name, description);
        return ResponseEntity.ok(
                FileResponseDto.builder()
                        .uuid(fileMetadata.getId())
                        .nameFile(fileMetadata.getName())
                        .description(fileMetadata.getDescription())
                        .name(fileMetadata.getHeader())
                        .build());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> fetchChunk(
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,
            @PathVariable UUID uuid
    ) {
        log.info(range);
        Range parsedRange = RangeCalculator.parseHttpRangeString(range, defaultChunkSize);
        ChunkWithMetadata chunkWithMetadata = chunkService.fetchChunk(uuid, parsedRange);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, chunkWithMetadata.metadata().getHttpContentType())
                .header(ACCEPT_RANGES, HttpConstants.ACCEPTS_RANGES_VALUE)
                .header(CONTENT_LENGTH,
                        HeadersUtil.calculateContentLengthHeader(parsedRange, chunkWithMetadata.metadata().getSize()))
                .header(CONTENT_RANGE,
                        HeadersUtil.constructContentRangeHeader(parsedRange, chunkWithMetadata.metadata().getSize()))
                .body(chunkWithMetadata.chunk());
    }

}
