package com.sarmad.moody.core.util

class FakeTimeProvider(
    private val isFixedTime: Boolean,
) : TimeProvider {

    val fixedTime = System.currentTimeMillis()

    override fun currentTimeMillis(): Long =
        if (isFixedTime) fixedTime else System.currentTimeMillis()
}