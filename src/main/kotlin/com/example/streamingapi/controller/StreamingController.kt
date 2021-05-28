package com.example.streamingapi.controller

import com.example.streamingapi.constants.StreamingConstants.Companion.BYTES
import com.example.streamingapi.constants.StreamingConstants.Companion.MP4
import com.example.streamingapi.constants.StreamingConstants.Companion.VIDEO
import com.example.streamingapi.data.VideoChunk
import com.example.streamingapi.extensions.addHeader
import com.example.streamingapi.service.StreamingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StreamingController(@Autowired val streamingService: StreamingService) {

    @GetMapping("/video")
    fun streamVideoChunk(@RequestParam(name = "v") videoName : String, @RequestHeader range : String) : ResponseEntity<ByteArray> {
        // Study the possibility to support different file formats
        val videoChunk : VideoChunk = streamingService.getChunk(videoName, MP4, range)

        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .headers(getResponseHeader(videoChunk))
                .body(videoChunk.content)
    }

    private fun getContentRange(start: Int, end: Int, fullLength: Int): String {
        return "bytes ${start}-${end - 1}/${fullLength}"
    }

    private fun getResponseHeader(videoChunk : VideoChunk) : HttpHeaders {
        val startByte : Int = videoChunk.startByte
        val endByte : Int = videoChunk.endByte
        val fullLength : Int = videoChunk.fullLength

        return HttpHeaders()
                .addHeader(HttpHeaders.CONTENT_RANGE, getContentRange(startByte, endByte, fullLength))
                .addHeader(HttpHeaders.ACCEPT_RANGES, BYTES)
                .addHeader(HttpHeaders.CONTENT_LENGTH, (videoChunk.chunkLength).toString())
                .addHeader(HttpHeaders.CONTENT_TYPE, "$VIDEO/$MP4")
    }
}