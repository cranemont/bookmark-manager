package com.kldaji.bookmark_manager.presentation.addbookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme

class AddBookmarkActivity : ComponentActivity() {

	companion object {
		const val TAG = "AddBookmarkActivity"
	}

	@OptIn(ExperimentalMaterialApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)


		intent.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
			// TODO: Call API
			Log.d(TAG, url)
		}

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val tags = listOf("SPORTS", "YOUTUBE")
				var title by remember { mutableStateOf(TextFieldValue("")) }
				var url by remember { mutableStateOf(TextFieldValue("")) }
				var description by remember { mutableStateOf(TextFieldValue("")) }

				Scaffold(
					topBar = {
						TopAppBar(
							title = {
								Text(text = "Add Bookmark")
							},
							backgroundColor = Color.White,
							navigationIcon = {
								IconButton(onClick = { /*TODO: 북마크 리스트 화면으로 이동 */ }) {
									Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "북마크 리스트 화면으로 이동")
								}
							}
						)
					}
				) { paddingValues ->

					Column(
						modifier = modifier
							.fillMaxSize()
							.padding(paddingValues)
							.padding(horizontal = 16.dp, vertical = 16.dp),
						verticalArrangement = Arrangement.SpaceBetween,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Column {
							LazyRow(modifier = modifier.fillMaxWidth()) {

								items(items = tags) { tag ->
									Chip(
										modifier = modifier.padding(end = 8.dp),
										onClick = { /*TODO*/ }
									) {
										Text(
											modifier = modifier.padding(end = 4.dp),
											text = "# $tag"
										)
									}
								}
							}

							Row(
								modifier = modifier.clickable { },
								verticalAlignment = Alignment.CenterVertically
							) {
								Icon(imageVector = Icons.Default.Add, contentDescription = "태그 추가", tint = Color.Blue)
								Text(
									text = "ADD TAG",
									fontSize = 14.sp,
									fontWeight = FontWeight.Bold,
									color = Color.Blue
								)
							}

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(top = 16.dp, bottom = 8.dp),
								value = url,
								onValueChange = { url = it },
								label = { Text(text = "URL") },
								maxLines = 1,
								singleLine = true
							)

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(vertical = 8.dp),
								value = title,
								onValueChange = { title = it },
								label = { Text(text = "TITLE") },
								maxLines = 1,
								singleLine = true
							)

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.height(200.dp)
									.padding(vertical = 8.dp),
								value = description,
								onValueChange = { description = it },
								label = { Text(text = "DESCRIPTION") },
							)
						}

						Button(
							modifier = modifier
								.width(250.dp)
								.padding(horizontal = 24.dp),
							onClick = { /*TODO*/ },
							colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
							enabled = true
						) {
							Text(
								text = "DONE",
								fontSize = 16.sp
							)
						}
					}
				}
			}
		}
	}
}
