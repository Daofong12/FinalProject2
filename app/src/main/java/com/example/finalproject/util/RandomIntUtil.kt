package com.example.finalproject.util

import java.util.concurrent.atomic.AtomicInteger

object RandomIntUtil {
    private val seed = AtomicInteger()

    fun getRandomInt(): Int = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}