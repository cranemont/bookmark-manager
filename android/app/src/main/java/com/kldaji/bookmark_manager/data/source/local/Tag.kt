package com.kldaji.bookmark_manager.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
	@PrimaryKey(autoGenerate = true) val id: Long,
	@ColumnInfo val name: String
)