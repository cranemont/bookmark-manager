package com.kldaji.bookmark_manager.data.entity

data class BookmarkNlp(
	val url: String = "",
	val title: String = "",
	val tags: List<String> = emptyList(),
	val summary: String = ""
)