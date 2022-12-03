package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.LoginResponse
import com.kldaji.bookmark_manager.data.entity.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

	@POST("/auth/login/")
	suspend fun signIn(@Body userInfo: UserInfo): String

	@POST("/auth/register/")
	suspend fun signUp(@Body userInfo: UserInfo): LoginResponse
}