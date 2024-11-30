package com.steven.data.utils

import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall<T>(private val delegate: Call<T>, private val gson: Gson) : Call<ApiResponse<T>> {
    override fun clone(): Call<ApiResponse<T>> {
        return ApiCall(delegate.clone(), gson)
    }

    override fun execute(): Response<ApiResponse<T>> {
        return Response.success(ApiResponse.create(delegate.execute(), gson))
    }

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                callback.onResponse(
                    this@ApiCall,
                    Response.success(ApiResponse.create(response, gson))
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@ApiCall,
                    Response.success(ApiResponse.create(t))
                )
            }
        })
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}
