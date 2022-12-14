package com.kldaji.bookmark_manager.presentation.bookmarks

import com.kldaji.bookmark_manager.data.entity.BookmarkResponse

data class BookmarksUiState(
	val allBookmarks: List<BookmarkResponse> = emptyList(),
	val bookmarkResponses: List<BookmarkResponse> = emptyList(),
	val groups: List<String> = emptyList(),
	val selectedGroup: String = groups.firstOrNull() ?: "",
	val selectedBookmarkId: String = "",
	val query: String = "",
	val queriedBookmarks: List<BookmarkResponse>? = null,
	val bookmarksUserMessage: String? = null,
	val searchUserMessage: String? = null,
	val showLoading: Unit? = null,
)