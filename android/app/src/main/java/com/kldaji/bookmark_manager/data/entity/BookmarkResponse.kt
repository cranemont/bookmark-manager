package com.kldaji.bookmark_manager.data.entity

data class BookmarkResponse(
	val id: String = "",
	val url: String = "",
	val title: String = "",
	val summary: String = "",
	val tags: List<String> = emptyList(),
	val group: String = ""
)