package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.entity.Group
import com.kldaji.bookmark_manager.data.entity.GroupResponse
import com.kldaji.bookmark_manager.data.source.local.GroupLocalDataSource
import com.kldaji.bookmark_manager.data.source.remote.GroupRemoteDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState
import com.kldaji.bookmark_manager.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
	private val groupLocalDataSource: GroupLocalDataSource,
	private val groupRemoteDataSource: GroupRemoteDataSource
) : GroupRepository {

	override fun getAll(): Flow<List<GroupUiState>> {
		return groupLocalDataSource
			.getAll()
			.map { tags ->
				tags.map { tag -> Mapper.groupToGroupUiState(tag) }
			}
	}

	override suspend fun insert(groupUiState: GroupUiState) {
		groupLocalDataSource.insert(Mapper.groupUiStateToGroup(groupUiState))
	}

	override suspend fun delete(groupUiState: GroupUiState) {
		groupLocalDataSource.delete(Mapper.groupUiStateToGroup(groupUiState))
	}

	override suspend fun getGroups(): Result<List<String>> {
		return groupRemoteDataSource.getGroups()
	}

	override suspend fun addGroup(group: Group): Result<GroupResponse> {
		return groupRemoteDataSource.addGroup(group)
	}
}