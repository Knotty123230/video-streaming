package com.v1.videostreamingmicroservice.util;

/**
 * The type Headers util.
 */
public class HeadersUtil {
    private HeadersUtil() {

    }

    /**
     * Calculate content length header string.
     *
     * @param range    the range
     * @param fileSize the file size
     * @return the string
     */
    public static String calculateContentLengthHeader(Range range, long fileSize) {
        return String.valueOf(range.getRangeEnd(fileSize) - range.getRangeStart() + 1);
    }

    /**
     * Construct content range header string.
     *
     * @param range    the range
     * @param fileSize the file size
     * @return the string
     */
    public static String constructContentRangeHeader(Range range, long fileSize) {
        return "bytes " + range.getRangeStart() + "-" + range.getRangeEnd(fileSize) + "/" + fileSize;
    }
}
