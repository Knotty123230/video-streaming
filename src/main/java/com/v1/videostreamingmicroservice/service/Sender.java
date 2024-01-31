package com.v1.videostreamingmicroservice.service;


/**
 * The interface Sender.
 */
public interface Sender {
    /**
     * Send t.
     *
     * @param <T>             the type parameter
     * @param fileMetadataDTO the file metadata dto
     * @param uuid            the uuid
     * @return the t
     */
    <T> T send(T fileMetadataDTO, String uuid);
}
