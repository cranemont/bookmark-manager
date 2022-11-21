package com.kldaji.bookmark_manager.data.entity

data class BookmarkResponse(
	val id: String = "",
	val url: String = "",
	val title: String = "",
	val summary: String = "",
	val tags: List<Tag> = emptyList(),
	val group: Group = Group(name = "")
)

data class Tag(val name: String)

data class Group(val name: String)