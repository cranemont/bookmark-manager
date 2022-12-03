package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.entity.LoginResponse
import com.kldaji.bookmark_manager.data.entity.UserInfo
import com.kldaji.bookmark_manager.data.source.remote.LoginRemoteDataSource
import com.kldaji.bookmark_manager.util.Result
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginRemoteDataSource: LoginRemoteDataSource) : LoginRepository {

	override suspend fun signIn(userInfo: UserInfo): Result<Unit> {
		return loginRemoteDataSource.signIn(userInfo)
	}

	override suspend fun signUp(userInfo: UserInfo): Result<LoginResponse> {
		return loginRemoteDataSource.signUp(userInfo)
	}
}