package com.kldaji.bookmark_manager.presentation.bookmarks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookmarkUiState(
	val id: Long = 0L, // will be auto generated
	val group: String,
	val tags: List<String>,
	val title: String,
	val url: String,
	val description: String,
): Parcelable