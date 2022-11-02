package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState

interface BookmarkRepository {
	fun getAll(): List<BookmarkUiState>
	fun insert(bookmarkUiState: BookmarkUiState)
	fun update(bookmarkUiState: BookmarkUiState)
	fun delete(bookmarkUiState: BookmarkUiState)
}