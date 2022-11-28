package com.kldaji.bookmark_manager.presentation.bookmarks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.GroupRepository
import com.kldaji.bookmark_manager.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository,
	private val groupRepository: GroupRepository
) : ViewModel() {

	var bookmarksUiState by mutableStateOf(BookmarksUiState())
		private set

	init {
		viewModelScope.launch {
			when (val result = groupRepository.getGroups()) {
				is Result.NetworkError -> Log.d("AddBookmarkActivity", result.toString())
				is Result.GenericError -> Log.d("AddBookmarkActivity", result.errorResponse?.message.toString())
				is Result.Success -> {
					val groups = result.data

					bookmarksUiState = bookmarksUiState.copy(groups = groups)
					setSelectedGroup(groups.firstOrNull())
				}
			}
		}
	}

	fun setSelectedGroup(name: String?) {
		name?.let {
			bookmarksUiState = bookmarksUiState.copy(selectedGroup = it)

			viewModelScope.launch {
				when (val result = bookmarkRepository.getBookmarksByGroup(it)) {
					is Result.NetworkError -> Log.d("AddBookmarkActivity", result.toString())
					is Result.GenericError -> Log.d("AddBookmarkActivity", result.errorResponse?.message.toString())
					is Result.Success -> {
						val bookmarkResponses = result.data
						bookmarksUiState = bookmarksUiState.copy(bookmarkResponses = bookmarkResponses)
					}
				}
			}
		}
	}

	fun getNewBookmarkResponses() {
		viewModelScope.launch {
			when (val result = bookmarkRepository.getBookmarksByGroup(bookmarksUiState.selectedGroup)) {
				is Result.NetworkError -> Log.d("AddBookmarkActivity", result.toString())
				is Result.GenericError -> Log.d("AddBookmarkActivity", result.errorResponse?.message.toString())
				is Result.Success -> {
					val bookmarkResponses = result.data
					Log.d("AddBookmarkActivity", bookmarkResponses.toString())
					bookmarksUiState = bookmarksUiState.copy(bookmarkResponses = bookmarkResponses)
				}
			}
		}
	}

	fun setSelectedBookmarkId(id: String) {
		bookmarksUiState = bookmarksUiState.copy(selectedBookmarkId = id)
	}
}