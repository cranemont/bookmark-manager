package com.kldaji.bookmark_manager.data

import com.kldaji.bookmark_manager.data.source.local.Group
import com.kldaji.bookmark_manager.data.entity.Bookmark
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState

object Mapper {

	fun bookmarkToBookmarkUiState(bookmark: Bookmark): BookmarkUiState {
		return BookmarkUiState(
			id = bookmark.id,
			group = bookmark.group,
			tags = bookmark.tags,
			title = bookmark.title,
			url = bookmark.url,
			description = bookmark.description
		)
	}

	fun bookmarkUiStateToBookmark(bookmarkUiState: BookmarkUiState): Bookmark {
		return Bookmark(
			id = bookmarkUiState.id,
			group = bookmarkUiState.group,
			tags = bookmarkUiState.tags,
			title = bookmarkUiState.title,
			url = bookmarkUiState.url,
			description = bookmarkUiState.description
		)
	}

	fun groupToGroupUiState(group: Group): GroupUiState {
		return GroupUiState(
			id = group.id,
			name = group.name.uppercase()
		)
	}

	fun groupUiStateToGroup(groupUiState: GroupUiState): Group {
		return Group(
			id = groupUiState.id,
			name = groupUiState.name.uppercase()
		)
	}
}