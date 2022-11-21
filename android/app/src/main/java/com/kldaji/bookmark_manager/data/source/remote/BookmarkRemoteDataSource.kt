package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.*
import com.kldaji.bookmark_manager.util.Result

interface BookmarkRemoteDataSource {

	suspend fun getBookmarkNlpResult(url: Url): Result<BookmarkNlp>

	suspend fun addBookmark(newBookmark: NewBookmark): Result<BookmarkResponse>

	suspend fun getBookmarksByGroup(name: String): Result<List<BookmarkResponse>>
}