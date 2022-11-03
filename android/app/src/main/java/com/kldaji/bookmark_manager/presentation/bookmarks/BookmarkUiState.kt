package com.kldaji.bookmark_manager.presentation.bookmarks

data class BookmarkUiState(
	val id: Long = 0L, // will be auto generated
	val tags: List<String>,
	val title: String,
	val url: String,
	val description: String,
)