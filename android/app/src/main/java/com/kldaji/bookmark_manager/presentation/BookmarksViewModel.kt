package com.kldaji.bookmark_manager.presentation

import androidx.lifecycle.ViewModel
import com.kldaji.bookmark_manager.data.repository.BookmarkRepository
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarkUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
	private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

	val tags = listOf("ALL", "SPORTS", "COMPUTER SCIENCE", "YOUTUBE")
	private val _bookmarks = listOf(
		BookmarkUiState(
			id = 0L,
			tags = listOf("SPORTS"),
			title = "손흥민 헤트트릭",
			url = "https://sports/son",
			description = "손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭 손흥민 헤트트릭"
		),
		BookmarkUiState(
			id = 1L,
			tags = listOf("COMPUTER SCIENCE"),
			title = "이진수 계산법",
			url = "https://binary/calculate",
			description = "이진수 계산법 이진수 계산법 이진수 계산법 이진수 계산법 이진수 계산법 이진수 계산법 이진수 계산법 이진수 계산법"
		),
		BookmarkUiState(
			id = 2L,
			tags = listOf("SPORTS"),
			title = "김민재 수비 하이라이트",
			url = "https://sports/kim",
			description = "김민재 수비 하이라이트 김민재 수비 하이라이트 김민재 수비 하이라이트 김민재 수비 하이라이트 김민재 수비 하이라이트 김민재 수비 하이라이트"
		),
		BookmarkUiState(
			id = 3L,
			tags = listOf("SPORTS", "YOUTUBE"),
			title = "황희찬 하이라이트",
			url = "https://youtube/hwang",
			description = "황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트 황희찬 하이라이트"
		)
	)
	val bookmarks: List<List<BookmarkUiState>>
		get() = tags.mapIndexed { index, tag ->
			when (index) {
				0 -> _bookmarks // ALL
				else -> _bookmarks.filter { bookmark -> bookmark.tags.contains(tag) }
			}
		}
}