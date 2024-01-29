package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;

public interface Sender {
    <T> T send(T fileMetadataDTO, String uuid);
}
