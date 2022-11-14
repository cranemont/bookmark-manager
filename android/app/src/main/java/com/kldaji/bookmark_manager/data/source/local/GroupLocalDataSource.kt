package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow

interface GroupLocalDataSource {
	fun getAll(): Flow<List<Group>>
	suspend fun insert(group: Group)
	suspend fun delete(group: Group)
}