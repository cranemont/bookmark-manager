package com.kldaji.bookmark_manager.di

import com.kldaji.bookmark_manager.data.repository.*
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
	abstract fun bindsGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository

	@Singleton
	@Binds
	abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}