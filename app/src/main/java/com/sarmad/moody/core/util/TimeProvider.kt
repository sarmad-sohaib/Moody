package com.sarmad.moody.core.util

import javax.inject.Inject

interface TimeProvider {
    fun currentTimeMillis(): Long
}

class DefaultTimeProvider @Inject constructor() : TimeProvider {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}
