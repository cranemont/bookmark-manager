package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.entity.BookmarkNlp
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import com.kldaji.bookmark_manager.data.source.remote.BookmarkRemoteDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
	private val bookmarkLocalDataSource: BookmarkLocalDataSource,
	private val bookmarkRemoteDataSource: BookmarkRemoteDataSource
) : BookmarkRepository {

	override fun getAll(): Flow<List<BookmarkUiState>> {
		return bookmarkLocalDataSource
			.getAll()
			.map { bookmarks ->
				bookmarks.map { bookmark -> Mapper.bookmarkToBookmarkUiState(bookmark) }
			}
	}

	override suspend fun insert(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.insert(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}

	override suspend fun update(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.update(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}

	override suspend fun delete(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.delete(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}

	override suspend fun getBookmarkNlpResult(url: Url): Result<BookmarkNlp> {
		return bookmarkRemoteDataSource.getBookmarkNlpResult(url)
	}

	override suspend fun addBookmark(newBookmark: NewBookmark): Result<BookmarkResponse> {
		return bookmarkRemoteDataSource.addBookmark(newBookmark)
	}

	override suspend fun getBookmarkById(id: String): Result<BookmarkResponse> {
		return bookmarkRemoteDataSource.getBookmarkById(id)
	}

	override suspend fun getBookmarksByGroup(name: String): Result<List<BookmarkResponse>> {
		return bookmarkRemoteDataSource.getBookmarksByGroup(name)
	}

	override suspend fun updateBookmark(id: String, newBookmark: NewBookmark): Result<BookmarkResponse> {
		return bookmarkRemoteDataSource.updateBookmark(id, newBookmark)
	}
}