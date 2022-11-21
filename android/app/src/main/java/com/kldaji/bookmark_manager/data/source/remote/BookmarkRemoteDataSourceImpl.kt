package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import com.kldaji.bookmark_manager.di.IoDispatcher
import com.kldaji.bookmark_manager.util.Result
import com.kldaji.bookmark_manager.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class BookmarkRemoteDataSourceImpl @Inject constructor(
	private val bookmarkApi: BookmarkApi,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BookmarkRemoteDataSource {

	override suspend fun getBookmarkNlpResult(url: Url): Result<BookmarkNlp> {
		return safeApiCall(ioDispatcher) {
			bookmarkApi.getBookmarkNlpResult(url)
		}
	}

	override suspend fun addBookmark(newBookmark: NewBookmark): Result<BookmarkResponse> {
		return safeApiCall(ioDispatcher) {
			bookmarkApi.addBookmark(newBookmark)
		}
	}
}