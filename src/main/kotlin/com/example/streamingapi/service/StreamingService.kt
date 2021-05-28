package com.example.streamingapi.service

import com.example.streamingapi.constants.StreamingConstants.Companion.CHUNK_SIZE
import com.example.streamingapi.util.getFileFromVideoName
import com.example.streamingapi.data.VideoChunk
import com.example.streamingapi.extensions.readChunk
import com.example.streamingapi.structures.FileDelayedCacheQueue
import org.springframework.stereotype.Component
import java.io.File
import java.io.InputStream
import java.util.Optional

@Component
class StreamingService {

    private val videoFileCache : FileDelayedCacheQueue = FileDelayedCacheQueue()

    // TODO: Find a solution for hashing the video's name
    fun getChunk(videoName : String, format : String, range : String) : VideoChunk {
        val videoFile : File = getVideoFile(videoName)
        val fileSize : Int = videoFile.length().toInt()
        val ranges : List<String> = range.split("-")

        val start : Int = ranges[0].replace("bytes=", "").toInt()
        val end : Int = (start + CHUNK_SIZE).coerceAtMost((fileSize))

        val inputStream : InputStream = videoFile.inputStream()
        val byteChunk : ByteArray = inputStream.readChunk(start, end)

        return VideoChunk(start, end, byteChunk.size, fileSize, byteChunk)
    }

    private fun getVideoFile(key : String) : File {
        return Optional
                .ofNullable(videoFileCache.get(key))
                .orElseGet { getFileFromVideoName(key) }
    }
}