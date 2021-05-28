package com.example.streamingapi.structures

import java.lang.ref.SoftReference
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

class DelayedCacheObject<T>(val key : String, val reference : SoftReference<T>, private val expirationTime : Long) : Delayed {
    override fun compareTo(other: Delayed?): Int {
        return when (other) {
            is DelayedCacheObject<*> -> when {
                this.expirationTime > other.expirationTime -> 1
                this.expirationTime == other.expirationTime -> 0
                this.expirationTime < other.expirationTime -> -1
                else -> -1
            }
            else -> -1
        }
    }

    override fun getDelay(unit: TimeUnit): Long {
        return unit.convert(expirationTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }
}