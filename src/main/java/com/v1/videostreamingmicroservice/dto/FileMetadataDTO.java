package com.v1.videostreamingmicroservice.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileMetadataDTO {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String header;
    @Field(type = FieldType.Text)
    private String description;
}
