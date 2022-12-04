package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.util.Result
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
	fun getAll(): Flow<List<BookmarkUiState>>
	suspend fun insert(bookmarkUiState: BookmarkUiState)
	suspend fun update(bookmarkUiState: BookmarkUiState)
	suspend fun delete(bookmarkUiState: BookmarkUiState)

	suspend fun getBookmarkNlpResult(url: Url): Result<BookmarkNlp>

	suspend fun addBookmark(newBookmark: NewBookmark): Result<BookmarkResponse>

	suspend fun getBookmarkById(id: String): Result<BookmarkResponse>

	suspend fun getBookmarksByGroup(name: String): Result<List<BookmarkResponse>>

	suspend fun updateBookmark(id: String, newBookmark: NewBookmark): Result<BookmarkResponse>

	suspend fun queryBookmarks(query: String): Result<List<BookmarkResponse>>

	suspend fun deleteBookmark(id: String): Result<Unit>

	suspend fun getAllBookmarks(): Result<List<BookmarkResponse>>
}