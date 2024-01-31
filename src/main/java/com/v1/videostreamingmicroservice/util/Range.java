package com.v1.videostreamingmicroservice.util;

import lombok.Builder;

/**
 * The type Range.
 */
@Builder
public class Range {

    private final long start;

    private final long end;

    /**
     * Gets range start.
     *
     * @return the range start
     */
    public long getRangeStart() {
        return start;
    }

    /**
     * Gets range end.
     *
     * @param fileSize the file size
     * @return the range end
     */
    public long getRangeEnd(long fileSize) {
        return Math.min(end, fileSize - 1);
    }


}
