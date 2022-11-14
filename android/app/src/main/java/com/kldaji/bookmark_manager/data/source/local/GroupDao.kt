package com.kldaji.bookmark_manager.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

	@Query("SELECT * FROM `Group`")
	fun getAll(): Flow<List<Group>>

	@Insert
	suspend fun insert(group: Group)

	@Delete
	suspend fun delete(groups: List<Group>)
}