package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow

interface TagLocalDataSource {
	fun getAll(): Flow<List<Tag>>
	suspend fun insert(tag: Tag)
	suspend fun delete(tag: Tag)
}