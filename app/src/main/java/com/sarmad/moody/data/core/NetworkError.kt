package com.sarmad.moody.data.core

sealed class NetworkError : Throwable() {
    object Network : NetworkError()
    object Unauthorized : NetworkError()
    object NotFound : NetworkError()
    object Server : NetworkError()
    object Unknown : NetworkError()
    data class Parsing(val reason: Throwable) : NetworkError()
}
