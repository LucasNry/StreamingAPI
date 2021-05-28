package com.example.streamingapi.structures

import com.example.streamingapi.constants.StreamingConstants.Companion.VIDEO_FILE_DEFAULT_TIME_TO_LIVE
import com.example.streamingapi.constants.StreamingConstants.Companion.VIDEO_FILE_MAX_CACHE_SIZE
import com.example.streamingapi.util.getFileFromVideoName
import java.io.File


class FileDelayedCacheQueue(override val timeToLive: Long = VIDEO_FILE_DEFAULT_TIME_TO_LIVE, maxCacheSize : Int = VIDEO_FILE_MAX_CACHE_SIZE) : DelayedQueueCache<File>(maxCacheSize) {
    override fun instantiateObject(key : String): File {
        return getFileFromVideoName(key)
    }
}