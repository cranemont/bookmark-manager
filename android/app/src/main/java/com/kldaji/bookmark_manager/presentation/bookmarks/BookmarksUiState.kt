package com.kldaji.bookmark_manager.presentation.bookmarks

data class BookmarksUiState(
	val bookmarkUiStates: List<BookmarkUiState> = emptyList(),
	val groupUiStates: List<GroupUiState> = emptyList(),
) {

	val filteredBookmarkUiStates: List<List<BookmarkUiState>> = groupUiStates.map { groupUiState ->
		bookmarkUiStates.filter { bookmarkUiState -> bookmarkUiState.group == groupUiState.name }
	}

	fun isEmpty(filteredBookmarkUiState: List<BookmarkUiState>) = filteredBookmarkUiState.isEmpty()
}