package com.steven.data.extensions

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody

inline fun <reified T> ResponseBody.parseErrorBody(gson: Gson): T? {
    return try {
        gson.fromJson(this.string(), T::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}