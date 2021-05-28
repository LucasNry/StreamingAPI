package com.example.streamingapi.extensions

import java.io.InputStream

fun InputStream.readChunk(from : Int, to : Int) : ByteArray {
    this.skip(from.toLong())

    val result = ByteArray((to) - from)
    this.read(result)

    return result
}