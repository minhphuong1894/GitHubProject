package com.steven.data.utils

data class ErrorBodyResponse(
    val statusCode: Int,
    val message: String?,
    val error: String?
)
