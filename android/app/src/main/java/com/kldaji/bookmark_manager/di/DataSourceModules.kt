package com.kldaji.bookmark_manager.di

import com.kldaji.bookmark_manager.data.source.local.*
import com.kldaji.bookmark_manager.data.source.remote.*
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
	abstract fun bindsGroupLocalDataSource(groupLocalDataSourceImpl: GroupLocalDataSourceImpl): GroupLocalDataSource

	@Singleton
	@Binds
	abstract fun bindsBookmarkRemoteDataSource(bookmarkRemoteDataSourceImpl: BookmarkRemoteDataSourceImpl): BookmarkRemoteDataSource

	@Singleton
	@Binds
	abstract fun bindsGroupRemoteDataSource(groupRemoteDataSourceImpl: GroupRemoteDataSourceImpl): GroupRemoteDataSource
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
	fun provideBookmarkApi(client: OkHttpClient, moshi: Moshi): BookmarkApi {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()
			.create(BookmarkApi::class.java)
	}

	@Singleton
	@Provides
	fun provideGroupApi(client: OkHttpClient, moshi: Moshi): GroupApi {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()
			.create(GroupApi::class.java)
	}
}