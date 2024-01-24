package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;

public interface Sender {
    FileMetadataDTO send(FileMetadataDTO fileMetadataDTO);
}
