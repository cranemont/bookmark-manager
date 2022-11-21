package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import retrofit2.http.Body
import retrofit2.http.POST

interface BookmarkApi {

	@POST("/nlp/summarize/")
	suspend fun getBookmarkNlpResult(@Body url: Url): BookmarkNlp

	@POST("/bookmark/")
	suspend fun addBookmark(@Body newBookmark: NewBookmark): BookmarkResponse
}