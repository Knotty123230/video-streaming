package com.v1.videostreamingmicroservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileResponseDto {
    private String uuid;
    private String name;
    private String description;
    private String nameFile;
}
