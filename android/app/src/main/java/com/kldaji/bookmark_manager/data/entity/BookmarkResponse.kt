package com.kldaji.bookmark_manager.data.entity

data class BookmarkResponse(
	val title: String = "",
	val topics: List<String> = listOf(),
	val summary: String = ""
)