package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkBody
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BookmarkApi {

	@POST("/nlp/summarize/")
	suspend fun getBookmarkResponse(@Body body: BookmarkBody): BookmarkResponse
}