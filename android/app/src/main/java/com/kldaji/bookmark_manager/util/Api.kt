package com.kldaji.bookmark_manager.util

import android.util.Log
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

data class ErrorResponse(
	val statusCode: Int,
	val message: String
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
				is HttpException -> Result.GenericError(parseErrorBody(throwable))
				else -> {
					Log.d("AddBookmarkActivity", throwable.message.toString())
					Result.GenericError(null)
				}
			}
		}
	}
}

private fun parseErrorBody(throwable: HttpException): ErrorResponse? {
	return try {
		throwable.response()?.errorBody()?.source()?.let {
			val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
			moshiAdapter.fromJson(it)
		}
	} catch (exception: Exception) {
		null
	}
}