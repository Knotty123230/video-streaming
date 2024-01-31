package com.v1.videostreamingmicroservice.dto;



import java.util.Arrays;
import java.util.Objects;


/**
 * The type Chunk with metadata.
 */
public record ChunkWithMetadata(
        FileMetadataDTO metadata,
        byte[] chunk
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkWithMetadata that = (ChunkWithMetadata) o;
        return Objects.equals(metadata, that.metadata) && Arrays.equals(chunk, that.chunk);
    }

    @Override
    public String toString() {
        return "ChunkWithMetadata{" +
                "metadata=" + metadata +
                ", chunk=" + Arrays.toString(chunk) +
                '}';
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(metadata);
        result = 31 * result + Arrays.hashCode(chunk);
        return result;
    }
}
