package com.v1.videostreamingmicroservice.exception;

/**
 * The type Storage exception.
 */
public class StorageException extends RuntimeException {

    /**
     * Instantiates a new Storage exception.
     *
     * @param ex the ex
     */
    public StorageException(Exception ex) {
        super(ex);
    }
}
