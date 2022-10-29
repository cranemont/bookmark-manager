package com.kldaji.bookmark_manager.presentation.addbookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme

class AddBookmarkActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			BookmarkmanagerTheme {
				intent.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
					Text(text = url)
				}
			}
		}
	}
}
