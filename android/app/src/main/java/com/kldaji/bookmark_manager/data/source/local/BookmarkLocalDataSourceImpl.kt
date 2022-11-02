package com.kldaji.bookmark_manager.data.source.local

import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
	private val bookmarkDao: BookmarkDao
) : BookmarkLocalDataSource {
	override fun getAll(): List<Bookmark> {
		return bookmarkDao.getAll()
	}

	override fun insert(bookmark: Bookmark) {
		bookmarkDao.insert(bookmark)
	}

	override fun update(bookmark: Bookmark) {
		bookmarkDao.update(bookmark)
	}

	override fun delete(bookmark: Bookmark) {
		bookmarkDao.delete(bookmark)
	}
}