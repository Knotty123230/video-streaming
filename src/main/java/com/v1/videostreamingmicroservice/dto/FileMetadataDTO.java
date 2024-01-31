package com.v1.videostreamingmicroservice.dto;

import lombok.*;

/**
 * The type File metadata dto.
 */
@Builder
public record FileMetadataDTO(
         String id,
         String header,
         String description,
         Long size,
         String name,
         String httpContentType
){

}
