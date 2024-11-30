package com.steven.data.utils

import com.google.gson.Gson
import com.steven.data.extensions.parseErrorBody
import retrofit2.Response
import java.net.HttpURLConnection

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "")
        }

        fun <T> create(response: Response<T>, gson: Gson): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val errorMessage =
                    response.errorBody()?.parseErrorBody<ErrorBodyResponse>(gson)?.message
                        ?: response.message()
                ApiBodyErrorResponse(
                    NetworkException(
                        code = response.code(),
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val message: String) : ApiResponse<T>()

data class ApiBodyErrorResponse<T>(val networkException: NetworkException) : ApiResponse<T>()