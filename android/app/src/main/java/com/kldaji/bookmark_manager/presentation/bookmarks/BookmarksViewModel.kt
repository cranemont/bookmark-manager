package com.kldaji.bookmark_manager.presentation.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.GroupRepository
import com.kldaji.bookmark_manager.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository,
	private val groupRepository: GroupRepository
) : ViewModel() {

	var bookmarksUiState by mutableStateOf(BookmarksUiState())
		private set

	private val _query = MutableStateFlow("")
	val query: StateFlow<String> = _query.asStateFlow()

	init {
		viewModelScope.launch {
			setShowLoading(Unit)

			when (val result = groupRepository.getGroups()) {
				is Result.NetworkError -> bookmarksUiState = bookmarksUiState.copy(bookmarksUserMessage = "please check network connection")
				is Result.GenericError -> bookmarksUiState = bookmarksUiState.copy(bookmarksUserMessage = result.errorResponse?.message)
				is Result.Success -> {
					val groups = result.data

					bookmarksUiState = bookmarksUiState.copy(groups = groups)
					setSelectedGroup(groups.firstOrNull())
				}
			}

			setShowLoading(null)
		}

		viewModelScope.launch {
			query.debounce(1000L).collect {
				bookmarksUiState = when (val result = bookmarkRepository.queryBookmarks(it)) {
					is Result.NetworkError -> bookmarksUiState.copy(searchUserMessage = "please check network connection")
					is Result.GenericError -> bookmarksUiState.copy(searchUserMessage = result.errorResponse?.message)
					is Result.Success -> {
						bookmarksUiState.copy(queriedBookmarks = result.data)
					}
				}
			}
		}

		viewModelScope.launch {
			setShowLoading(Unit)

			bookmarksUiState = when (val result = bookmarkRepository.getAllBookmarks()) {
				is Result.NetworkError -> bookmarksUiState.copy(bookmarksUserMessage = "please check network connection")
				is Result.GenericError -> bookmarksUiState.copy(bookmarksUserMessage = result.errorResponse?.message)
				is Result.Success -> {
					bookmarksUiState.copy(allBookmarks = result.data)
				}
			}

			setShowLoading(null)
		}
	}

	fun hideUserMessage() {
		bookmarksUiState = bookmarksUiState.copy(
			bookmarksUserMessage = null,
			searchUserMessage = null
		)
	}

	private fun setShowLoading(showLoading: Unit?) {
		bookmarksUiState = bookmarksUiState.copy(showLoading = showLoading)
	}


	fun setSelectedGroup(name: String?) {
		name?.let {
			bookmarksUiState = bookmarksUiState.copy(selectedGroup = it)

			viewModelScope.launch {
				setShowLoading(Unit)

				bookmarksUiState = when (val result = bookmarkRepository.getBookmarksByGroup(it)) {
					is Result.NetworkError -> bookmarksUiState.copy(bookmarksUserMessage = "please check network connection")
					is Result.GenericError -> bookmarksUiState.copy(bookmarksUserMessage = result.errorResponse?.message)
					is Result.Success -> {
						val bookmarkResponses = result.data
						bookmarksUiState.copy(bookmarkResponses = bookmarkResponses)
					}
				}

				setShowLoading(null)
			}
		}
	}

	fun getNewBookmarkResponses() {
		viewModelScope.launch {
			setShowLoading(Unit)

			bookmarksUiState = when (val result = bookmarkRepository.getBookmarksByGroup(bookmarksUiState.selectedGroup)) {
				is Result.NetworkError -> bookmarksUiState.copy(bookmarksUserMessage = "please check network connection")
				is Result.GenericError -> bookmarksUiState.copy(bookmarksUserMessage = result.errorResponse?.message)
				is Result.Success -> {
					val bookmarkResponses = result.data
					bookmarksUiState.copy(bookmarkResponses = bookmarkResponses)
				}
			}

			setShowLoading(null)
		}
	}

	fun setSelectedBookmarkId(id: String) {
		bookmarksUiState = bookmarksUiState.copy(selectedBookmarkId = id)
	}

	fun setQuery(query: String) {
		_query.value = query
		bookmarksUiState = bookmarksUiState.copy(
			query = query,
			queriedBookmarks = null
		)
	}

	fun deleteBookmark(id: String) {
		viewModelScope.launch {
			setShowLoading(Unit)

			when (val result = bookmarkRepository.deleteBookmark(id)) {
				is Result.NetworkError -> bookmarksUiState = bookmarksUiState.copy(bookmarksUserMessage = "please check network connection")
				is Result.GenericError -> bookmarksUiState = bookmarksUiState.copy(bookmarksUserMessage = result.errorResponse?.message)
				is Result.Success -> getNewBookmarkResponses()
			}

			setShowLoading(null)
		}
	}
}