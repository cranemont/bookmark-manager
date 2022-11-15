package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupLocalDataSourceImpl @Inject constructor(
	private val groupDao: GroupDao
) : GroupLocalDataSource {

	override fun getAll(): Flow<List<Group>> {
		return groupDao.getAll()
	}

	override suspend fun insert(group: Group) {
		groupDao.insert(group)
	}

	override suspend fun delete(group: Group) {
		groupDao.delete(group)
	}
}