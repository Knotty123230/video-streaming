package com.v1.videostreamingmicroservice.util;

import lombok.Builder;

@Builder
public class Range {

    private final long start;

    private final long end;

    public long getRangeStart() {
        return start;
    }

    public long getRangeEnd(long fileSize) {
        return Math.min(end, fileSize - 1);
    }


}
