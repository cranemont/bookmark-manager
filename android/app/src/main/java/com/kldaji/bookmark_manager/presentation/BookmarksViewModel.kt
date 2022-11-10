package com.kldaji.bookmark_manager.presentation

import androidx.lifecycle.*
import com.kldaji.bookmark_manager.data.entity.BookmarkBody
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
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

	private val _bookmarkResponse = MutableStateFlow(BookmarkResponse())
	val bookmarkResponse: LiveData<BookmarkResponse>
		get() = _bookmarkResponse.asLiveData()

	private val _isShowProgressBar = MutableLiveData<Boolean>()
	val isShowProgressBar: LiveData<Boolean>
		get() = _isShowProgressBar

	init {
		viewModelScope.launch {
			bookmarkRepository
				.getAll()
				.collect { newBookmarks ->
					_bookmarks.value = newBookmarks
				}
		}
		viewModelScope.launch {
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

	fun addTag(tag: String) {
		viewModelScope.launch {
			tagRepository.insert(TagUiState(name = tag))
		}
	}

	fun deleteTags(tagUiStates: List<TagUiState>, deletedTagNames: List<String>) {
		viewModelScope.launch {
			val deletedTagUiStates = mutableListOf<TagUiState>()

			deletedTagNames.forEach { tagName: String ->
				val deletedTagUiState = tagUiStates.find { tagUiState -> tagUiState.name == tagName }

				if (deletedTagUiState != null) deletedTagUiStates.add(deletedTagUiState)
			}
			tagRepository.delete(deletedTagUiStates)
		}
	}

	fun setBookmarkResponse(url: String) {
		viewModelScope.launch {
			showProgressBar()
			bookmarkRepository.getBookmarkResponse(BookmarkBody(url))
				.collect { result ->
					when (result) {
						is com.kldaji.bookmark_manager.util.Result.NetworkError -> {
							println("NETWORK ERROR")
						}
						is com.kldaji.bookmark_manager.util.Result.GenericError -> {
							println("GENERIC ERROR")
						}
						is com.kldaji.bookmark_manager.util.Result.Success -> _bookmarkResponse.value = result.data
					}
					hideProgressBar()
				}
		}
	}

	private fun showProgressBar() {
		_isShowProgressBar.value = true
	}

	private fun hideProgressBar() {
		_isShowProgressBar.value = false
	}
}