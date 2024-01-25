package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender implements Sender {
    private final KafkaTemplate<String, FileMetadataDTO> kafkaTemplate;
    @Value("${topic.send-order}")
    private String sendClientTopic;


    @Override
    public FileMetadataDTO send(FileMetadataDTO fileMetadataDTO) {
        kafkaTemplate.send(sendClientTopic, fileMetadataDTO.getId(), fileMetadataDTO);
        return fileMetadataDTO;
    }
}
