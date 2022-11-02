package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.source.local.Bookmark
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
	private val bookmarkLocalDataSource: BookmarkLocalDataSource
): BookmarkRepository {

	override fun getAll(): List<Bookmark> {
		return bookmarkLocalDataSource.getAll()
	}

	override fun insert(bookmark: Bookmark) {
		bookmarkLocalDataSource.insert(bookmark)
	}

	override fun update(bookmark: Bookmark) {
		bookmarkLocalDataSource.update(bookmark)
	}

	override fun delete(bookmark: Bookmark) {
		bookmarkLocalDataSource.delete(bookmark)
	}
}