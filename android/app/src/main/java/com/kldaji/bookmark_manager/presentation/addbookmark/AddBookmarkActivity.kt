package com.kldaji.bookmark_manager.presentation.addbookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kldaji.bookmark_manager.presentation.BookmarksViewModel
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarksActivity
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookmarkActivity : ComponentActivity() {

	companion object {
		const val TAG = "AddBookmarkActivity"
	}

	@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		intent.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
			// TODO: Call API
			Log.d(TAG, url)
		}

		val bookmarksViewModel: BookmarksViewModel by viewModels()

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				var isExpanded by remember { mutableStateOf(false) }

				val tags by bookmarksViewModel.tags.observeAsState(mutableListOf())
				var selectedTags by remember { mutableStateOf(listOf<String>()) }

				var title by remember { mutableStateOf(TextFieldValue("")) }
				var url by remember { mutableStateOf(TextFieldValue("")) }
				var description by remember { mutableStateOf(TextFieldValue("")) }

				var isShowDialog by remember { mutableStateOf(false) }
				var tag by remember { mutableStateOf(TextFieldValue("")) }

				Scaffold(
					topBar = {
						TopAppBar(
							title = {
								Text(text = "Add Bookmark")
							},
							backgroundColor = Color.White,
							navigationIcon = {
								IconButton(onClick = {
									val intent = Intent(this, BookmarksActivity::class.java)
									startActivity(intent)
									finish()
								}) {
									Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "북마크 리스트 화면으로 이동")
								}
							}
						)
					}
				) { paddingValues ->

					if (isShowDialog) {
						Dialog(onDismissRequest = {
							isShowDialog = false
							tag = TextFieldValue("")
						}) {
							Column(
								modifier = modifier
									.fillMaxWidth()
									.wrapContentHeight()
									.clip(RoundedCornerShape(12.dp))
									.background(Color.White)
									.padding(vertical = 8.dp),
								horizontalAlignment = Alignment.CenterHorizontally,
								verticalArrangement = Arrangement.Center
							) {
								OutlinedTextField(
									value = tag,
									onValueChange = { tag = it },
									label = {
										Text(text = "TAG")
									},
									maxLines = 1,
									singleLine = true
								)

								Row(
									modifier = modifier
										.fillMaxWidth()
										.padding(top = 8.dp),
									horizontalArrangement = Arrangement.SpaceEvenly
								) {
									Button(
										onClick = {
											isShowDialog = false
											tag = TextFieldValue("")
										},
										colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
									) {
										Text(text = "취소")
									}

									Button(
										onClick = {
											bookmarksViewModel.addTag(tag.text)
											isShowDialog = false
											tag = TextFieldValue("")
										},
										colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
									) {
										Text(text = "추가")
									}
								}
							}
						}
					}

					Column(
						modifier = modifier
							.fillMaxSize()
							.padding(paddingValues)
							.padding(horizontal = 16.dp, vertical = 16.dp),
						verticalArrangement = Arrangement.SpaceBetween,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Column {
							Column(
								modifier = modifier
									.fillMaxWidth()
									.animateContentSize()
							) {
								Row(
									modifier = modifier
										.fillMaxWidth()
										.clickable { isExpanded = !isExpanded }
										.border(
											width = 1.dp,
											color = Color.DarkGray
										)
										.padding(horizontal = 16.dp, vertical = 8.dp),
									horizontalArrangement = Arrangement.SpaceBetween,
									verticalAlignment = Alignment.CenterVertically
								) {
									Text(
										text = "TAGS",
										fontWeight = FontWeight.Bold,
										color = Color.Black
									)
									if (!isExpanded) Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "태그 리스트 확장")
									else Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "태그 리스트 축소")
								}

								if (isExpanded) {
									Row(
										modifier = modifier
											.padding(top = 6.dp)
											.clickable { isShowDialog = !isShowDialog },
										verticalAlignment = Alignment.CenterVertically
									) {
										Icon(
											imageVector = Icons.Default.Add,
											contentDescription = "태그 추가",
											tint = Color.Blue
										)
										Text(
											text = "ADD TAG",
											color = Color.Blue,
											fontSize = 14.sp,
											fontWeight = FontWeight.Bold
										)
									}

									LazyHorizontalStaggeredGrid(
										modifier = modifier
											.height(140.dp)
											.padding(top = 12.dp),
										rows = StaggeredGridCells.Fixed(3),
									) {
										items(items = tags) { tagUiState ->
											Chip(
												modifier = modifier.padding(end = 8.dp, bottom = 8.dp),
												onClick = {
													val newSelectedTags = selectedTags.toMutableList()

													if (newSelectedTags.contains(tagUiState.name)) newSelectedTags.remove(tagUiState.name)
													else newSelectedTags.add(tagUiState.name)

													selectedTags = newSelectedTags
												},
												leadingIcon = {
													if (selectedTags.contains(tagUiState.name)) {
														Icon(
															Icons.Default.Check,
															contentDescription = "태그 선택"
														)
													}
												},
												colors = if (selectedTags.contains(tagUiState.name)) ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.secondary) else ChipDefaults.chipColors()
											) {

												Text(
													modifier = modifier.padding(end = 4.dp),
													text = tagUiState.name
												)
											}
										}
									}
								}
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
							onClick = {
								bookmarksViewModel.addBookmark(selectedTags, title.text, url.text, description.text)
								val intent = Intent(this@AddBookmarkActivity, BookmarksActivity::class.java)
								startActivity(intent)
								finish()
							},
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
