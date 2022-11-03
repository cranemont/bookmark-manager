package com.kldaji.bookmark_manager.di

import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSourceImpl
import com.kldaji.bookmark_manager.data.source.local.TagLocalDataSource
import com.kldaji.bookmark_manager.data.source.local.TagLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Singleton
	@Binds
	abstract fun bindsBookmarkLocalDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource

	@Singleton
	@Binds
	abstract fun bindsTagLocalDataSource(tagkLocalDataSourceImpl: TagLocalDataSourceImpl): TagLocalDataSource
}