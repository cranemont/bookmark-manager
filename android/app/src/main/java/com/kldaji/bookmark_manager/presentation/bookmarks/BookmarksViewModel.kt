package com.kldaji.bookmark_manager.presentation.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.GroupRepository
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
			bookmarkRepository
				.getAll()
				.collect { newBookmarks ->
					bookmarksUiState = bookmarksUiState.copy(bookmarkUiStates = newBookmarks)
				}
		}
		viewModelScope.launch {
			groupRepository
				.getAll()
				.collect { newGroups ->
					bookmarksUiState = bookmarksUiState.copy(groupUiStates = newGroups)
				}
		}
	}

	fun removeGroup(groupUiState: GroupUiState) {
		viewModelScope.launch {
			groupRepository.delete(groupUiState)
		}
	}
}