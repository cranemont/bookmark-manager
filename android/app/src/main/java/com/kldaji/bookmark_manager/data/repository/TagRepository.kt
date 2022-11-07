package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.presentation.bookmarks.TagUiState
import kotlinx.coroutines.flow.Flow

interface TagRepository {
	fun getAll(): Flow<List<TagUiState>>
	suspend fun insert(tagUiState: TagUiState)
	suspend fun delete(tagUiStates: List<TagUiState>)
}