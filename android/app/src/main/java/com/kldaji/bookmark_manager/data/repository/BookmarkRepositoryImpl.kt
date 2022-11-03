package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
	private val bookmarkLocalDataSource: BookmarkLocalDataSource
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
}