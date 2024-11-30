package com.steven.data.utils

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapter(private val gson: Gson) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }
        val upperBound = getParameterUpperBound(0, returnType)
        return if (upperBound is ParameterizedType &&
            upperBound.rawType == ApiResponse::class.java
        ) {
            object : CallAdapter<Any, Call<ApiResponse<*>>> {
                override fun responseType(): Type = getParameterUpperBound(0, upperBound)

                override fun adapt(call: Call<Any>): Call<ApiResponse<*>> =
                    ApiCall(call, gson) as Call<ApiResponse<*>>
            }
        } else {
            null
        }
    }
}
