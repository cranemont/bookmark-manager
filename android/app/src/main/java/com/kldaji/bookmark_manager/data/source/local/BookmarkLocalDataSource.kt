package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {
	fun getAll(): Flow<Bookmark>
	suspend fun insert(bookmark: Bookmark)
	suspend fun update(bookmark: Bookmark)
	suspend fun delete(bookmark: Bookmark)
}