package main.kotlin.com.example.streamingapi.service

import com.example.streamingapi.data.VideoChunk
import com.example.streamingapi.service.StreamingService
import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File

class StreamingServiceTest {
    companion object {
        const val video1 : String = "toystory"
        const val video2 : String = "bigbuck"

        const val MP4 : String = "mp4"
    }

    private var streamingService: StreamingService = StreamingService()

    @ParameterizedTest
    @ValueSource(strings = [video1, video2])
    fun testStreamingIntegrity(videoName : String) {
        val file : File = File("resources/$videoName.$MP4")
        val fileSize : Int = file.length().toInt()
        val expectedOutput : ByteArray = file.readBytes()

        var initialChunk = "bytes=0-"
        val videoBuffer : ByteArray = ByteArray(fileSize)

        do {
            val videoChunk : VideoChunk = streamingService.getChunk(videoName , MP4, initialChunk)
            val start : Int = videoChunk.startByte
            val end : Int = videoChunk.endByte

            videoChunk.content.copyInto(videoBuffer, start)
            initialChunk = "bytes=$end-"
        } while (end != fileSize)

        for (i in 0 until fileSize) {
            Assert.assertEquals(expectedOutput[i], videoBuffer[i])
        }

        Assert.assertEquals(expectedOutput.size, videoBuffer.size)
    }
}