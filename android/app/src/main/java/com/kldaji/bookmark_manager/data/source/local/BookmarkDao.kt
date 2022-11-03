package com.kldaji.bookmark_manager.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

	@Query("SELECT * FROM Bookmark")
	fun getAll(): Flow<Bookmark>

	@Insert
	suspend fun insert(bookmark: Bookmark)

	@Update
	suspend fun update(bookmark: Bookmark)

	@Delete
	suspend fun delete(bookmark: Bookmark)
}