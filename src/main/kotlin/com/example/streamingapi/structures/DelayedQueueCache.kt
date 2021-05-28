package com.example.streamingapi.structures

import java.lang.ref.SoftReference
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.DelayQueue

abstract class DelayedQueueCache<T>(private val maxCacheSize : Int) {

    private val cache : ConcurrentHashMap<String, SoftReference<T>> = ConcurrentHashMap()
    private val delayedQueue: DelayQueue<DelayedCacheObject<T>> = DelayQueue()
    protected abstract val timeToLive : Long

    init {
        val cleanerThread : Thread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val delayedCacheObject : DelayedCacheObject<T> = delayedQueue.take()
                    cache.remove(delayedCacheObject.key, delayedCacheObject.reference)
                } catch (exception : InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }

        cleanerThread.isDaemon = true
        cleanerThread.start()
    }

    protected fun add(key : String, value : T) {
        val timeToExpire : Long = System.currentTimeMillis() + timeToLive
        val softReference : SoftReference<T> = SoftReference(value)
        cache[key] = softReference
        delayedQueue.put(DelayedCacheObject(key, softReference, timeToExpire))
    }

    fun remove(key : String) {
        cache.remove(key)
    }

    fun get(key : String) : T? {
        val cacheObject : SoftReference<T>? = cache[key]

        return when {
            cacheObject != null -> cacheObject.get()
            else -> createObject(key)
        }
    }

    fun clear() {
        cache.clear()
    }

    fun isFull() : Boolean {
        return cache.size >= maxCacheSize
    }

    private fun createObject(key : String) : T? {
        if (!isFull()) {
            val instantiatedObject : T = instantiateObject(key)
            add(key, instantiatedObject)

            return instantiatedObject
        }

        return null
    }

    protected abstract fun instantiateObject(key : String) : T
}