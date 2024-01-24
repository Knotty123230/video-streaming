package com.v1.videostreamingmicroservice.repository;

import com.v1.videostreamingmicroservice.entity.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRelationalRepository extends JpaRepository<FileMetadataEntity, String> {
}
