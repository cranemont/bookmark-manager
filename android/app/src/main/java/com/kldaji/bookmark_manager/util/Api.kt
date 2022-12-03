package com.kldaji.bookmark_manager.util

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


data class ErrorResponse(
	val statusCode: Int = 0,
	val message: String = "",
	val error: String = ""
)

sealed class Result<out T> {
	data class Success<out T>(val data: T) : Result<T>()
	data class GenericError(val errorResponse: ErrorResponse?) : Result<Nothing>()
	object NetworkError : Result<Nothing>()
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
	return withContext(dispatcher) {
		try {
			Result.Success(apiCall.invoke())
		} catch (throwable: Throwable) {
			when (throwable) {
				is IOException -> Result.NetworkError
				is HttpException -> {
					Log.d("LoginViewModel", "HTTPEXCEPTION")
					Result.GenericError(parseErrorBody(throwable))
				}
				else -> {
					Log.d("LoginViewModel", "OTHERS")
					Result.GenericError(null)
				}
			}
		}
	}
}

private fun parseErrorBody(throwable: HttpException): ErrorResponse? {
	val errorBody = throwable.response()?.errorBody()?.string()

	return try {
		errorBody?.let {
			val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(ErrorResponse::class.java)
			moshi.fromJson(it)
		}
	} catch (exception: Exception) {
		null
	}
}