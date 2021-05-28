package com.example.streamingapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StreamingApiApplication

fun main(args: Array<String>) {
    runApplication<StreamingApiApplication>(*args)
}
