package com.kldaji.bookmark_manager.presentation

import androidx.lifecycle.*
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.data.repository.TagRepository
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import com.kldaji.bookmark_manager.presentation.bookmarks.TagUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository,
	private val tagRepository: TagRepository
) : ViewModel() {

	private val _bookmarks = MutableStateFlow(listOf<BookmarkUiState>())
	val bookmarks: LiveData<List<List<BookmarkUiState>>>
	get() = combine(_bookmarks, _tags) { bookmarkUiStates, tagUiStates ->
		(listOf(TagUiState(name = "ALL")) + tagUiStates).mapIndexed { index, tagUiState ->
			when (index) {
				0 -> bookmarkUiStates // ALL
				else -> bookmarkUiStates.filter { bookmark -> bookmark.tags.contains(tagUiState.name) }
			}
		}
	}.asLiveData()

	private val _tags = MutableStateFlow(listOf<TagUiState>())
	val tags: LiveData<List<TagUiState>>
		get() = _tags.asLiveData()

	val tagsWithAll: LiveData<List<TagUiState>>
		get() = _tags
			.asLiveData()
			.map { tagUiStates ->
				listOf(TagUiState(name = "ALL")) + tagUiStates
			}

	init {
		viewModelScope.launch {
			bookmarkRepository
				.getAll()
				.collect { newBookmarks ->
					_bookmarks.value = newBookmarks
				}
			tagRepository
				.getAll()
				.collect { newTags ->
					_tags.value = newTags
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