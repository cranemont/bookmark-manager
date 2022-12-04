package com.kldaji.bookmark_manager.presentation.addbookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.entity.Group
import com.kldaji.bookmark_manager.data.entity.NewBookmark
import com.kldaji.bookmark_manager.data.entity.Url
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.GroupRepository
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
		getGroups()
	}

	private fun getGroups() {
		viewModelScope.launch {
			addBookmarkUiState = when (val result = groupRepository.getGroups()) {
				is Result.NetworkError -> addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> {
					val groups = result.data

					addBookmarkUiState.copy(groups = groups)
				}
			}
		}
	}

	fun setBookmarkId(id: String) {
		addBookmarkUiState = addBookmarkUiState.copy(bookmarkId = id)
	}

	private fun setShowLoading(showLoading: Boolean) {
		addBookmarkUiState = addBookmarkUiState.copy(showLoading = showLoading)
	}

	fun getBookmarkById(id: String) {
		viewModelScope.launch {
			setShowLoading(true)

			addBookmarkUiState = when (val result = bookmarkRepository.getBookmarkById(id)) {
				is Result.NetworkError -> addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> {
					val bookmarkResponse = result.data

					addBookmarkUiState.copy(
						url = TextFieldValue(bookmarkResponse.url),
						title = TextFieldValue(bookmarkResponse.title),
						description = TextFieldValue(bookmarkResponse.summary),
						tags = bookmarkResponse.tags.map { it },
						selectedGroup = bookmarkResponse.group
					)
				}
			}

			setShowLoading(false)
		}
	}

	fun addBookmark() {
		viewModelScope.launch {
			setShowLoading(true)

			val newBookmark = NewBookmark().copy(
				url = addBookmarkUiState.url.text,
				title = addBookmarkUiState.title.text,
				summary = addBookmarkUiState.description.text,
				tags = addBookmarkUiState.tags,
				group = addBookmarkUiState.selectedGroup
			)

			addBookmarkUiState = when (val result = bookmarkRepository.addBookmark(newBookmark)) {
				is Result.NetworkError -> addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> addBookmarkUiState.copy(navigateToMain = Unit)
			}

			setShowLoading(false)
		}
	}

	fun updateBookmark() {
		viewModelScope.launch {
			setShowLoading(true)

			val newBookmark = NewBookmark().copy(
				url = addBookmarkUiState.url.text,
				title = addBookmarkUiState.title.text,
				summary = addBookmarkUiState.description.text,
				tags = addBookmarkUiState.tags,
				group = addBookmarkUiState.selectedGroup
			)

			addBookmarkUiState = when (val result = bookmarkRepository.updateBookmark(addBookmarkUiState.bookmarkId, newBookmark)) {
				is Result.NetworkError -> addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> addBookmarkUiState.copy(navigateToMain = Unit)
			}

			setShowLoading(false)
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
			setShowLoading(true)

			when (val result = groupRepository.addGroup(Group(group))) {
				is Result.NetworkError -> addBookmarkUiState = addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState = addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> getGroups()
			}

			setShowLoading(false)
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

	fun setBookmarkResponse(url: Url) {
		viewModelScope.launch {
			setShowLoading(true)

			val result = bookmarkRepository.getBookmarkNlpResult(url)
			addBookmarkUiState = when (result) {
				is Result.NetworkError -> addBookmarkUiState.copy(userMessage = "please check network connection")
				is Result.GenericError -> addBookmarkUiState.copy(userMessage = result.errorResponse?.message)
				is Result.Success -> addBookmarkUiState.copy(bookmarkNlp = result.data)
			}

			setShowLoading(false)
		}
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