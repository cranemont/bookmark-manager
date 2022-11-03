package com.kldaji.bookmark_manager.presentation

import androidx.lifecycle.*
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

	val tags = listOf("ALL", "SPORTS", "COMPUTER SCIENCE", "YOUTUBE")
	private val _bookmarks = MutableStateFlow(listOf<BookmarkUiState>())
	val bookmarks: LiveData<List<List<BookmarkUiState>>>
		get() = _bookmarks
			.asLiveData()
			.map { liveBookmarks ->
				tags.mapIndexed { index, tag ->
					when (index) {
						0 -> liveBookmarks // ALL
						else -> liveBookmarks.filter { bookmark -> bookmark.tags.contains(tag) }
					}
				}
			}

	init {
		viewModelScope.launch {
			bookmarkRepository
				.getAll()
				.collect { newBookmarks ->
					_bookmarks.value = newBookmarks
				}
		}

	}

	fun addBookmark(
		tags: List<String>,
		title: String,
		url: String,
		description: String
	) {
		viewModelScope.launch {
			bookmarkRepository.insert(
				BookmarkUiState(
					tags = tags,
					title = title,
					url = url,
					description = description
				)
			)
		}
	}
}