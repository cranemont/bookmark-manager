package com.kldaji.bookmark_manager.ui.bookmarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kldaji.bookmark_manager.ui.theme.BookmarkmanagerTheme

class BookmarksActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier

				Scaffold(
					topBar = {
						TopAppBar(
							title = {
								Text(text = "Bookmark Manager")
							},
							actions = {
								IconButton(onClick = { /* TODO : 최근 북마크 리스트 화면으로 이동 */ }) {
									Icon(
										imageVector = Icons.Default.History,
										contentDescription = "최근 북마크 리스트 화면으로 이동"
									)
								}
							},
							backgroundColor = Color.White,
							elevation = 0.dp
						)
					},
					floatingActionButton = {
						FloatingActionButton(onClick = { /* TODO : 북마크 추가 화면으로 이동 */ }) {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "북마크 추가 화면으로 이동"
							)
						}
					}
				) { paddingValues ->

					Text(
						modifier = modifier.padding(paddingValues),
						text = "body..."
					)
				}
			}
		}
	}
}
