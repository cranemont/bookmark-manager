package com.kldaji.bookmark_manager.data.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
	private val bookmarkDao: BookmarkDao
) : BookmarkLocalDataSource {

	override fun getAll(): Flow<Bookmark> {
		return bookmarkDao.getAll()
	}

	override suspend fun insert(bookmark: Bookmark) {
		bookmarkDao.insert(bookmark)
	}

	override suspend fun update(bookmark: Bookmark) {
		bookmarkDao.update(bookmark)
	}

	override suspend fun delete(bookmark: Bookmark) {
		bookmarkDao.delete(bookmark)
	}
}