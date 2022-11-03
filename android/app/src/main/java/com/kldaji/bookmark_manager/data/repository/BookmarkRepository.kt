package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
	fun getAll(): Flow<BookmarkUiState>
	suspend fun insert(bookmarkUiState: BookmarkUiState)
	suspend fun update(bookmarkUiState: BookmarkUiState)
	suspend fun delete(bookmarkUiState: BookmarkUiState)
}