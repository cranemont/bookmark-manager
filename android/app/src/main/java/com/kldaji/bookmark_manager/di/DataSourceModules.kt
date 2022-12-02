package com.kldaji.bookmark_manager.di

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kldaji.bookmark_manager.data.source.local.*
import com.kldaji.bookmark_manager.data.source.remote.*
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

	@Singleton
	@Binds
	abstract fun bindsLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
	private const val BASE_URL = "https://nother.ml"

	@Singleton
	@Provides
	fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
		val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
		val httpLoggingInterceptor = HttpLoggingInterceptor()
		httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
		val headerInterceptor = HttpLoggingInterceptor()
		httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

		return OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS)
			.addInterceptor(headerInterceptor)
			.addInterceptor(httpLoggingInterceptor)
			.cookieJar(cookieJar)
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

	@Singleton
	@Provides
	fun providesLoginApi(client: OkHttpClient, moshi: Moshi): LoginApi {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
			.client(client)
			.build()
			.create(LoginApi::class.java)
	}
}