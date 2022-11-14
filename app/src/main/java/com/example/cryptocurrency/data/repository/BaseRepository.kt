package com.example.cryptocurrency.data.repository

import com.example.cryptocurrency.common.error.GenericError
import com.example.cryptocurrency.common.error.ServerError
import com.example.cryptocurrency.data.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <R> handleNetwork(call: suspend () -> R): R {
        try {
            return call()
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.let {
                parseErrorBody(it)
            }
            throw ServerError(
                code = response?.code,
                response?.message ?: e.message(),
                e.stackTraceToString()
            )
        } catch (e: Exception) {
            throw GenericError(e.message, e.stackTraceToString())
        }
    }

    private fun parseErrorBody(errorBody: ResponseBody): Response<Any> {
        val type = object : TypeToken<Response<Any>>() {}.type
        return Gson().fromJson(errorBody.charStream(), type)
    }
}
