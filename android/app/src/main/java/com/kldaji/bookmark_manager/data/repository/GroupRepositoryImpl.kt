package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.source.local.GroupLocalDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
	private val groupLocalDataSource: GroupLocalDataSource
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

	override suspend fun delete(groupUiStates: List<GroupUiState>) {
		val tags = groupUiStates.map { tagUiState -> Mapper.groupUiStateToGroup(tagUiState) }
		groupLocalDataSource.delete(tags)
	}
}