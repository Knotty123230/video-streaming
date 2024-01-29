package com.v1.videostreamingmicroservice.dto;

import lombok.*;
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
