package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import retrofit2.http.*

interface BookmarkApi {

	@POST("/nlp/summarize/")
	suspend fun getBookmarkNlpResult(@Body url: Url): BookmarkNlp

	@POST("/bookmark/")
	suspend fun addBookmark(@Body newBookmark: NewBookmark): BookmarkResponse

	@PUT("/bookmark/{id}/")
	suspend fun updateBookmark(@Path("id") id: String, @Body newBookmark: NewBookmark): BookmarkResponse

	@DELETE("/bookmark/{id}/")
	suspend fun deleteBookmark(@Path("id") id: String): Unit

	@GET("/bookmark/{id}/")
	suspend fun getBookmarkById(@Path("id") id: String): BookmarkResponse

	@GET("/bookmarks")
	suspend fun getBookmarksByTag(@Query("name") tag: String): List<BookmarkResponse>

	@GET("/bookmarks/group")
	suspend fun getBookmarksByGroup(@Query("name") name: String): List<BookmarkResponse>

	@GET("/bookmarks/search")
	suspend fun queryBookmarks(@Query("query") query: String): List<BookmarkResponse>
}