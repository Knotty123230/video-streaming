package com.v1.videostreamingmicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataEntity {
    @Id
    private String id;
    private String name;
    private String header;
    private String description;
    private long size;
    private String httpContentType;
}
