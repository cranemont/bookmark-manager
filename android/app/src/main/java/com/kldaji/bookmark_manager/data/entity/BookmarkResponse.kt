package com.kldaji.bookmark_manager.data.entity

data class BookmarkResponse(
	val url: String = "",
	val title: String = "",
	val tags: List<String> = listOf(),
	val summary: String = ""
)