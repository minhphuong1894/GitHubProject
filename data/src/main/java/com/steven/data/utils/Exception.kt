package com.steven.data.utils

data class NetworkException(
    @ResponseCode val code: Int,
    val errorMessage: String
) : Exception(errorMessage)

class EmptyException : Exception()