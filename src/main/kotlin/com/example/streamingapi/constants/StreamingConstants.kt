package com.example.streamingapi.constants

class StreamingConstants {
    companion object {
        const val CHUNK_SIZE : Int = 1000000 // 1MB
        const val VIDEO_REPOSITORY : String = "resources"

        const val BYTES : String = "bytes"
        const val VIDEO : String = "video"

        // Supported File Formats
        const val MP4 : String = "mp4"

        // DelayedQueueCache constants
        const val VIDEO_FILE_MAX_CACHE_SIZE : Int = 25
        const val VIDEO_FILE_DEFAULT_TIME_TO_LIVE : Long = 240000 // 4 min
    }
}
