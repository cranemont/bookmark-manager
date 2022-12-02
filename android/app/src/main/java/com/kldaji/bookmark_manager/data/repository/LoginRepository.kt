package com.kldaji.bookmark_manager.data.repository

import com.kldaji.bookmark_manager.data.entity.LoginResponse
import com.kldaji.bookmark_manager.data.entity.UserInfo
import com.kldaji.bookmark_manager.util.Result

interface LoginRepository {

	suspend fun signIn(userInfo: UserInfo): Result<String>

	suspend fun signUp(userInfo: UserInfo): Result<LoginResponse>
}