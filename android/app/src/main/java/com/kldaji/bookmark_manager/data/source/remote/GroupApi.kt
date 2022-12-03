package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.Group
import com.kldaji.bookmark_manager.data.entity.GroupResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupApi {

	@GET("/groups/")
	suspend fun getGroups(): List<String>

	@POST("/group/")
	suspend fun addGroup(@Body group: Group): GroupResponse
}