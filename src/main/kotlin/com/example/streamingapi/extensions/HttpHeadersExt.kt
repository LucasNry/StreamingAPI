package com.example.streamingapi.extensions

import org.springframework.http.HttpHeaders

fun HttpHeaders.addHeader(headerName : String, headerValue : String?) : HttpHeaders {
    this.set(headerName, headerValue ?: "")

    return this
}