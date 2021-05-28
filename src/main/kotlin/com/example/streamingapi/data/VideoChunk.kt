package com.example.streamingapi.data

data class VideoChunk(val startByte : Int, val endByte : Int, val chunkLength : Int, val fullLength : Int, val content : ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoChunk

        if (startByte != other.startByte) return false
        if (endByte != other.endByte) return false
        if (chunkLength != other.chunkLength) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startByte
        result = 31 * result + endByte
        result = 31 * result + chunkLength
        result = 31 * result + content.contentHashCode()
        return result
    }
}