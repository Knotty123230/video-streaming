package com.v1.videostreamingmicroservice.service;

import com.v1.videostreamingmicroservice.dto.FileMetadataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Kafka sender.
 */
@Service
@RequiredArgsConstructor
public class KafkaSender implements Sender {
    private final KafkaTemplate<String, FileMetadataDTO> kafkaTemplate;
    @Value("${topic.send-order}")
    private String sendClientTopic;


    @Override
    public <T> T send(T fileMetadataDTO, String uuid) {
        kafkaTemplate.send(sendClientTopic, uuid, (FileMetadataDTO) fileMetadataDTO);
        return fileMetadataDTO;
    }
}
