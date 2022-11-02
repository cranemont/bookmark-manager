package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.source.local.Bookmark

interface BookmarkRepository {
	fun getAll(): List<Bookmark>
	fun insert(bookmark: Bookmark)
	fun update(bookmark: Bookmark)
	fun delete(bookmark: Bookmark)
}