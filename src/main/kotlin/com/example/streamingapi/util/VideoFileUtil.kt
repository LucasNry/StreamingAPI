package com.example.streamingapi.util

import com.example.streamingapi.constants.StreamingConstants.Companion.MP4
import com.example.streamingapi.constants.StreamingConstants.Companion.VIDEO_REPOSITORY
import java.io.File

fun getFileFromVideoName(key : String, format : String = MP4) : File {
    return File("${VIDEO_REPOSITORY}/$key.$format")
}