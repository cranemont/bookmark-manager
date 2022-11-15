package com.kldaji.bookmark_manager.presentation.addbookmark

import androidx.compose.ui.text.input.TextFieldValue
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState

data class AddBookmarkUiState(
	val bookmarkUiState: BookmarkUiState? = null,
	val tags: List<String> = emptyList(),
	val groupUiStates: List<GroupUiState> = emptyList(),
	val selectedGroup: String = "",
	val isShowAddGroupDialog: Boolean = false,
	val bookmarkResponse: BookmarkResponse? = null,
	val isShowProgressBar: Boolean = false,
	val isShowGroups: Boolean = false,
	val title: TextFieldValue = TextFieldValue(""),
	val url: TextFieldValue = TextFieldValue(""),
	val description: TextFieldValue = TextFieldValue(""),
	val isShowAddTagDialog: Boolean = false,
	val newTag: TextFieldValue = TextFieldValue(""),
	val newGroup: TextFieldValue = TextFieldValue(""),
	val isDuplicatedTag: Boolean = false,
	val isDuplicatedGroup: Boolean = false,
	val isNetworkError: Boolean = false
) {

	fun createNewBookmarkUiState(): BookmarkUiState {
		return BookmarkUiState(
			group = selectedGroup,
			tags = tags,
			title = title.text,
			url = url.text,
			description = description.text
		)
	}
}