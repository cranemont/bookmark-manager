package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.GroupResponse
import com.kldaji.bookmark_manager.di.IoDispatcher
import com.kldaji.bookmark_manager.util.Result
import com.kldaji.bookmark_manager.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GroupRemoteDataSourceImpl @Inject constructor(
	private val groupApi: GroupApi,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GroupRemoteDataSource {

	override suspend fun getGroups(): Result<List<String>> {
		return safeApiCall(ioDispatcher) {
			groupApi.getGroups()
		}
	}

	override suspend fun addGroup(group: String): Result<GroupResponse> {
		return safeApiCall(ioDispatcher) {
			groupApi.addGroup(group)
		}
	}
}