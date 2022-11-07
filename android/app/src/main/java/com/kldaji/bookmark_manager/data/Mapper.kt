package com.kldaji.bookmark_manager.data

import com.kldaji.bookmark_manager.data.source.local.Tag
import com.kldaji.bookmark_manager.data.entity.Bookmark
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.presentation.bookmarks.TagUiState

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

	fun tagToTagUiState(tag: Tag): TagUiState {
		return TagUiState(
			id = tag.id,
			name = tag.name
		)
	}

	fun tagUiStateToTag(tagUiState: TagUiState): Tag {
		return Tag(
			id = tagUiState.id,
			name = tagUiState.name
		)
	}
}