package com.kldaji.bookmark_manager.presentation.bookmarks

import com.kldaji.bookmark_manager.data.entity.BookmarkResponse

data class BookmarksUiState(
	val bookmarkResponses: List<BookmarkResponse> = emptyList(),
	val groups: List<String> = emptyList(),
	val selectedGroup: String = groups.firstOrNull() ?: "",
	val selectedBookmarkId: String = "",
	val query: String = "",
	val queriedBookmarks: List<BookmarkResponse> = emptyList()
)