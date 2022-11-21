package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.Group
import com.kldaji.bookmark_manager.data.entity.GroupResponse
import com.kldaji.bookmark_manager.util.Result

interface GroupRemoteDataSource {

	suspend fun getGroups(): Result<List<String>>
	suspend fun addGroup(group: Group): Result<GroupResponse>
}