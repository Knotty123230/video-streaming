package com.v1.videostreamingmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Video not found exception.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VideoNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Video not found exception.
     *
     * @param message the message
     */
    public VideoNotFoundException(String message) {
        super(message);
    }
}
