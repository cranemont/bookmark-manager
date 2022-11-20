package com.kldaji.bookmark_manager.presentation.bookmarks

data class BookmarksUiState(
	val bookmarkUiStates: List<BookmarkUiState> = emptyList(),
	val groupUiStates: List<GroupUiState> = emptyList(),
	val selectedGroup: String = groupUiStates.firstOrNull()?.name ?: "",
) {

	val groupedBookmarkUiStates: List<BookmarkUiState> = bookmarkUiStates.filter { bookmarkUiState ->
		selectedGroup == bookmarkUiState.group
	}
}