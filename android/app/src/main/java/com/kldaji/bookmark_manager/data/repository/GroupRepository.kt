package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
	fun getAll(): Flow<List<GroupUiState>>
	suspend fun insert(groupUiState: GroupUiState)
	suspend fun delete(groupUiState: GroupUiState)
}