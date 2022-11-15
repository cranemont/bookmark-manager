package com.kldaji.bookmark_manager.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kldaji.bookmark_manager.data.entity.Bookmark
import com.kldaji.bookmark_manager.util.StringListTypeConverter

@Database(entities = [Bookmark::class, Group::class], version = 1)
@TypeConverters(value = [StringListTypeConverter::class])
abstract class BookmarkDatabase : RoomDatabase() {
	abstract fun bookmarkDao(): BookmarkDao
	abstract fun groupDao(): GroupDao
}