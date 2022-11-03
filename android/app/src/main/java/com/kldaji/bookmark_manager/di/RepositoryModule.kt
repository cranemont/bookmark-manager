package com.kldaji.bookmark_manager.di

import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.BookmarkRepositoryImpl
import com.kldaji.bookmark_manager.data.repository.TagRepository
import com.kldaji.bookmark_manager.data.repository.TagRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Singleton
	@Binds
	abstract fun bindsBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository

	@Singleton
	@Binds
	abstract fun bindsTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository
}