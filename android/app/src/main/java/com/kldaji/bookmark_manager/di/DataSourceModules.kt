package com.kldaji.bookmark_manager.di

import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSource
import com.kldaji.bookmark_manager.data.source.local.BookmarkLocalDataSourceImpl
import com.kldaji.bookmark_manager.data.source.local.TagLocalDataSource
import com.kldaji.bookmark_manager.data.source.local.TagLocalDataSourceImpl
import com.kldaji.bookmark_manager.data.source.remote.BookmarkRemoteDataSource
import com.kldaji.bookmark_manager.data.source.remote.BookmarkRemoteDataSourceImpl
import com.kldaji.bookmark_manager.data.source.remote.BookmarkApi
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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

	@Singleton
	@Binds
	abstract fun bindsBookmarkRemoteDataSource(bookmarkRemoteDataSourceImpl: BookmarkRemoteDataSourceImpl): BookmarkRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
	private const val BASE_URL = "http://43.201.119.242"

	@Singleton
	@Provides
	fun providesOkHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS)
			.build()
	}

	@Singleton
	@Provides
	fun provideServiceApi(client: OkHttpClient, moshi: Moshi): BookmarkApi {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()
			.create(BookmarkApi::class.java)
	}
}