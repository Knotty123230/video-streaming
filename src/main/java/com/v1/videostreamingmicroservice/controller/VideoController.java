package com.v1.videostreamingmicroservice.controller;


import com.v1.videostreamingmicroservice.constants.HttpConstants;
import com.v1.videostreamingmicroservice.dto.ChunkWithMetadata;
import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import com.v1.videostreamingmicroservice.dto.FileResponseDto;
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

/**
 * The type Video controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {


    private final VideoService videoService;
    private final ChunkService chunkService;

    /**
     * The Default chunk size.
     */
    @Value("${photon.streaming.default-chunk-size}")
    public Integer defaultChunkSize;

    /**
     * Save response entity.
     *
     * @param file        the file
     * @param name        the name
     * @param description the description
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileResponseDto> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        FileMetadataDTO fileMetadata = videoService.save(file, name, description);
        return ResponseEntity.ok(
                FileResponseDto.builder()
                        .uuid(fileMetadata.id())
                        .nameFile(fileMetadata.name())
                        .description(fileMetadata.description())
                        .name(fileMetadata.header())
                        .build());
    }

    /**
     * Fetch chunk response entity.
     *
     * @param range the range
     * @param uuid  the uuid
     * @return the response entity
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> fetchChunk(
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,
            @PathVariable UUID uuid
    ) {
        log.info(range);
        Range parsedRange = RangeCalculator.parseHttpRangeString(range, defaultChunkSize);
        ChunkWithMetadata chunkWithMetadata = chunkService.fetchChunk(uuid, parsedRange);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, chunkWithMetadata.metadata().httpContentType())
                .header(ACCEPT_RANGES, HttpConstants.ACCEPTS_RANGES_VALUE)
                .header(CONTENT_LENGTH,
                        HeadersUtil.calculateContentLengthHeader(parsedRange, chunkWithMetadata.metadata().size()))
                .header(CONTENT_RANGE,
                        HeadersUtil.constructContentRangeHeader(parsedRange, chunkWithMetadata.metadata().size()))
                .body(chunkWithMetadata.chunk());
    }

}
