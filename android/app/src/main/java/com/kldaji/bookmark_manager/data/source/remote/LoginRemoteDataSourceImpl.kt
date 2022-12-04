package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.LoginResponse
import com.kldaji.bookmark_manager.data.entity.UserInfo
import com.kldaji.bookmark_manager.di.IoDispatcher
import com.kldaji.bookmark_manager.util.Result
import com.kldaji.bookmark_manager.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
	private val loginApi: LoginApi,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
): LoginRemoteDataSource {

	override suspend fun signIn(userInfo: UserInfo): Result<Unit> {
		return safeApiCall(ioDispatcher) {
			loginApi.signIn(userInfo)
		}
	}

	override suspend fun signUp(userInfo: UserInfo): Result<LoginResponse> {
		return safeApiCall(ioDispatcher) {
			loginApi.signUp(userInfo)
		}
	}
}