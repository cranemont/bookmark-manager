package com.kldaji.bookmark_manager.presentation.addbookmark

import androidx.compose.ui.text.input.TextFieldValue
import com.kldaji.bookmark_manager.data.entity.BookmarkNlp

data class AddBookmarkUiState(
	val tags: List<String> = emptyList(),
	val groups: List<String> = emptyList(),
	val selectedGroup: String = "",
	val isShowAddGroupDialog: Boolean = false,
	val bookmarkNlp: BookmarkNlp? = null,
	val isShowGroups: Boolean = false,
	val title: TextFieldValue = TextFieldValue(""),
	val url: TextFieldValue = TextFieldValue(""),
	val description: TextFieldValue = TextFieldValue(""),
	val newTag: String = "",
	val newGroup: TextFieldValue = TextFieldValue(""),
	val isDuplicatedTag: Boolean = false,
	val isDuplicatedGroup: Boolean = false,
	val isNetworkError: Boolean = false,
	val bookmarkId: String = "",
	val navigateToMain: Unit? = null,
	val userMessage: String? = null,
	val showLoading: Boolean = false,
) {

	val isEdit: Boolean = bookmarkId.isNotEmpty()

	val topAppBarTitle: String = if (isEdit) "Edit Bookmark" else "Add Bookmark"
}