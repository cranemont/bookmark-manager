package com.kldaji.bookmark_manager.data.source.local

import androidx.room.*

@Dao
interface BookmarkDao {

	@Query("SELECT * FROM Bookmark")
	fun getAll(): List<Bookmark>

	@Insert
	fun insert(bookmark: Bookmark)

	@Update
	fun update(bookmark: Bookmark)

	@Delete
	fun delete(bookmark: Bookmark)
}