package com.sarmad.moody.core.util

class FakeTimeProvider() : TimeProvider {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}