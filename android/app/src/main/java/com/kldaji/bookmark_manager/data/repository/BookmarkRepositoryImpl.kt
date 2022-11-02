package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
	private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

	override fun getAll(): List<BookmarkUiState> {
		return bookmarkLocalDataSource
			.getAll()
			.map { bookmark ->
				Mapper.bookmarkToBookmarkUiState(bookmark)
			}
	}

	override fun insert(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.insert(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}

	override fun update(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.update(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}

	override fun delete(bookmarkUiState: BookmarkUiState) {
		bookmarkLocalDataSource.delete(Mapper.bookmarkUiStateToBookmark(bookmarkUiState))
	}
}