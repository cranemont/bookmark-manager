package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagLocalDataSourceImpl @Inject constructor(
	private val tagDao: TagDao
) : TagLocalDataSource {

	override fun getAll(): Flow<List<Tag>> {
		return tagDao.getAll()
	}

	override suspend fun insert(tag: Tag) {
		tagDao.insert(tag)
	}

	override suspend fun delete(tags: List<Tag>) {
		tagDao.delete(tags)
	}
}