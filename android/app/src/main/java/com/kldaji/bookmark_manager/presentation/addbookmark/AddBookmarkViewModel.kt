package com.kldaji.bookmark_manager.presentation.addbookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.entity.BookmarkBody
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.GroupRepository
import com.kldaji.bookmark_manager.presentation.bookmarks.GroupUiState
import com.kldaji.bookmark_manager.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookmarkViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository,
	private val groupRepository: GroupRepository
) : ViewModel() {

	var addBookmarkUiState by mutableStateOf(AddBookmarkUiState())
		private set

	init {
		viewModelScope.launch {
			groupRepository
				.getAll()
				.collect { groupUiStates ->
					addBookmarkUiState = addBookmarkUiState.copy(groupUiStates = groupUiStates)
				}
		}
	}

	fun addBookmarkUiState() {
		addBookmarkUiState = addBookmarkUiState.copy(bookmarkUiState = addBookmarkUiState.createNewBookmarkUiState())

		viewModelScope.launch {
			addBookmarkUiState.bookmarkUiState?.let {
				bookmarkRepository.insert(it)
			}
		}
	}

	fun addTag(tag: String) {
		if (tag.isEmpty()) return

		val list = addBookmarkUiState.tags.toMutableList()

		if (list.contains(tag)) {
			addBookmarkUiState = addBookmarkUiState.copy(isDuplicatedTag = true)
			return
		}

		list.add(tag)
		addBookmarkUiState = addBookmarkUiState.copy(tags = list)
	}

	fun addTags(tags: List<String>) {
		val list = addBookmarkUiState.tags.toMutableList()

		for (tag in tags) {
			if (list.contains(tag)) {
				addBookmarkUiState = addBookmarkUiState.copy(isDuplicatedTag = true)
				continue
			}
			list.add(tag)
		}
		addBookmarkUiState = addBookmarkUiState.copy(tags = list)
	}

	fun removeTag(tag: String) {
		val list = addBookmarkUiState.tags.toMutableList()

		list.remove(tag)
		addBookmarkUiState = addBookmarkUiState.copy(tags = list)
	}

	fun addGroup(group: String) {
		viewModelScope.launch {
			groupRepository.insert(GroupUiState(name = group))
		}
	}

	fun setSelectedGroup(group: String) {
		addBookmarkUiState = addBookmarkUiState.copy(selectedGroup = group)
	}

	fun showAddGroupDialog() {
		addBookmarkUiState = addBookmarkUiState.copy(isShowAddGroupDialog = true)
	}

	fun hideAddGroupDialog() {
		addBookmarkUiState = addBookmarkUiState.copy(
			isShowAddGroupDialog = false,
			newGroup = TextFieldValue("")
		)
	}

	fun setBookmarkResponse(url: String) {
		viewModelScope.launch {
			showProgressBar()

			bookmarkRepository.getBookmarkResponse(BookmarkBody(url))
				.collect { result ->
					addBookmarkUiState = when (result) {
						is Result.NetworkError -> addBookmarkUiState.copy(isNetworkError = true)
						is Result.GenericError -> addBookmarkUiState.copy(isNetworkError = true)
						is Result.Success -> addBookmarkUiState.copy(bookmarkResponse = result.data)
					}

					hideProgressBar()
				}
		}
	}

	private fun showProgressBar() {
		addBookmarkUiState = addBookmarkUiState.copy(isShowProgressBar = true)
	}

	private fun hideProgressBar() {
		addBookmarkUiState = addBookmarkUiState.copy(isShowProgressBar = false)
	}

	fun showGroups() {
		addBookmarkUiState = addBookmarkUiState.copy(isShowGroups = true)
	}

	fun hideGroups() {
		addBookmarkUiState = addBookmarkUiState.copy(isShowGroups = false)
	}

	fun setTitle(textFieldValue: TextFieldValue) {
		addBookmarkUiState = addBookmarkUiState.copy(title = textFieldValue)
	}

	fun setUrl(textFieldValue: TextFieldValue) {
		addBookmarkUiState = addBookmarkUiState.copy(url = textFieldValue)
	}

	fun setDescription(textFieldValue: TextFieldValue) {
		addBookmarkUiState = addBookmarkUiState.copy(description = textFieldValue)
	}

	fun setNewTag(tag: String) {
		addBookmarkUiState = addBookmarkUiState.copy(newTag = tag)
	}

	fun setNewGroup(textFieldValue: TextFieldValue) {
		if (addBookmarkUiState.isShowAddGroupDialog) {
			addBookmarkUiState = addBookmarkUiState.copy(newGroup = textFieldValue)
		}
	}

	fun doneDuplicatedTag() {
		addBookmarkUiState = addBookmarkUiState.copy(isDuplicatedTag = false)
	}

	fun doneDuplicatedGroup() {
		addBookmarkUiState = addBookmarkUiState.copy(isDuplicatedGroup = false)
	}
}