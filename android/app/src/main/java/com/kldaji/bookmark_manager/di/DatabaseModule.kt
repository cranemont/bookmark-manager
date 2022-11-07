package com.kldaji.bookmark_manager.di

import android.content.Context
import androidx.room.Room
import com.kldaji.bookmark_manager.data.source.local.BookmarkDao
import com.kldaji.bookmark_manager.data.source.local.BookmarkDatabase
import com.kldaji.bookmark_manager.data.source.local.TagDao
import com.kldaji.bookmark_manager.util.StringListTypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Singleton
	@Provides
	fun providesMoshi(): Moshi {
		return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	}

	@Singleton
	@Provides
	fun providesBookmarkDatabase(
		@ApplicationContext context: Context,
		moshi: Moshi
	): BookmarkDatabase {
		return Room.databaseBuilder(
			context,
			BookmarkDatabase::class.java,
			"bookmark"
		)
			.addTypeConverter(StringListTypeConverter(moshi))
			.build()
	}

	@Singleton
	@Provides
	fun providesBookmarkDao(bookmarkDatabase: BookmarkDatabase): BookmarkDao {
		return bookmarkDatabase.bookmarkDao()
	}

	@Singleton
	@Provides
	fun providesTagDao(bookmarkDatabase: BookmarkDatabase): TagDao {
		return bookmarkDatabase.tagDao()
	}

}