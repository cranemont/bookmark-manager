package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import com.kldaji.bookmark_manager.util.Result

interface BookmarkRemoteDataSource {

	suspend fun getBookmarkNlpResult(url: Url): Result<BookmarkNlp>

	suspend fun addBookmark(newBookmark: NewBookmark): Result<BookmarkResponse>
}