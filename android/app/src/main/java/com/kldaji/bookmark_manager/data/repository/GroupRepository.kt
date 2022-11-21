package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.entity.Group
import com.kldaji.bookmark_manager.data.entity.GroupResponse
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState
import com.kldaji.bookmark_manager.util.Result
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
	fun getAll(): Flow<List<GroupUiState>>
	suspend fun insert(groupUiState: GroupUiState)
	suspend fun delete(groupUiState: GroupUiState)

	suspend fun getGroups(): Result<List<String>>
	suspend fun addGroup(group: Group): Result<GroupResponse>
}