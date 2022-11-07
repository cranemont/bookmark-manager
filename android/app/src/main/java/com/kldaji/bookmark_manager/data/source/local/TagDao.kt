package com.kldaji.bookmark_manager.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

	@Query("SELECT * FROM Tag")
	fun getAll(): Flow<List<Tag>>

	@Insert
	suspend fun insert(tag: Tag)

	@Delete
	suspend fun delete(tags: List<Tag>)
}