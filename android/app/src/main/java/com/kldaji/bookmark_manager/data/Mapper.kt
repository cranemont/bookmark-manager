package com.kldaji.bookmark_manager.data

import com.kldaji.bookmark_manager.data.source.local.Bookmark
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState

object Mapper {

	fun bookmarkToBookmarkUiState(bookmark: Bookmark): BookmarkUiState {
		return BookmarkUiState(
			id = bookmark.id,
			tags = bookmark.tags,
			title = bookmark.title,
			url = bookmark.url,
			description = bookmark.description
		)
	}

	fun bookmarkUiStateToBookmark(bookmarkUiState: BookmarkUiState): Bookmark {
		return Bookmark(
			id = bookmarkUiState.id,
			tags = bookmarkUiState.tags,
			title = bookmarkUiState.title,
			url = bookmarkUiState.url,
			description = bookmarkUiState.description
		)
	}
}