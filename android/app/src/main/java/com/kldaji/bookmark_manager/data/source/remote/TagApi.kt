package com.kldaji.bookmark_manager.data.source.remote

import retrofit2.http.GET

interface TagApi {

	@GET("/tags/")
	suspend fun getTags(): List<String>
}