package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.Mapper
import com.kldaji.bookmark_manager.data.source.local.TagLocalDataSource
import com.kldaji.bookmark_manager.presentation.bookmarks.TagUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
	private val tagLocalDataSource: TagLocalDataSource
) : TagRepository {

	override fun getAll(): Flow<List<TagUiState>> {
		return tagLocalDataSource
			.getAll()
			.map { tags ->
				tags.map { tag -> Mapper.tagToTagUiState(tag) }
			}
	}

	override suspend fun insert(tagUiState: TagUiState) {
		tagLocalDataSource.insert(Mapper.tagUiStateToTag(tagUiState))
	}

	override suspend fun delete(tagUiStates: List<TagUiState>) {
		val tags = tagUiStates.map { tagUiState -> Mapper.tagUiStateToTag(tagUiState) }
		tagLocalDataSource.delete(tags)
	}
}