package com.kldaji.bookmark_manager.data.source.local

interface BookmarkLocalDataSource {
	fun getAll(): List<Bookmark>
	fun insert(bookmark: Bookmark)
	fun update(bookmark: Bookmark)
	fun delete(bookmark: Bookmark)
}