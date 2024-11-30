package com.steven.data.utils

import androidx.annotation.IntDef
import java.net.HttpURLConnection

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    value = [
        HttpURLConnection.HTTP_OK,
        HttpURLConnection.HTTP_CREATED,
        HttpURLConnection.HTTP_NO_CONTENT,
        HttpURLConnection.HTTP_BAD_REQUEST,
        HttpURLConnection.HTTP_UNAUTHORIZED,
        HttpURLConnection.HTTP_FORBIDDEN,
        HttpURLConnection.HTTP_NOT_FOUND,
        HttpURLConnection.HTTP_CLIENT_TIMEOUT,
        HttpURLConnection.HTTP_CONFLICT,
        HttpURLConnection.HTTP_UNAVAILABLE,
        HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
        HttpURLConnection.HTTP_BAD_GATEWAY,
    ]
)
annotation class ResponseCode