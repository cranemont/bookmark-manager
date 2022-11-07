package com.kldaji.bookmark_manager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
	@PrimaryKey(autoGenerate = true) val id: Long,
	@ColumnInfo val tags: List<String>,
	@ColumnInfo val title: String,
	@ColumnInfo val url: String,
	@ColumnInfo val description: String,
)