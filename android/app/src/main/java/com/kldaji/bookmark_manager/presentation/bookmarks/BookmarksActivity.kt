package com.kldaji.bookmark_manager.presentation.bookmarks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kldaji.bookmark_manager.presentation.addbookmark.AddBookmarkActivity
import com.kldaji.bookmark_manager.presentation.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val bookmarksViewModel: BookmarksViewModel by viewModels()

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val coroutineScope = rememberCoroutineScope()
				val bookmarksUiState = bookmarksViewModel.bookmarksUiState
				val scaffoldState = rememberScaffoldState()

				Scaffold(
					topBar = {
						TopAppBar(
							title = {
								Text(text = bookmarksUiState.selectedGroup)
							},
							actions = {
								IconButton(onClick = { /* TODO : 최근 북마크 리스트 화면으로 이동 */ }) {
									Icon(
										imageVector = Icons.Default.History,
										contentDescription = "최근 북마크 리스트 화면으로 이동"
									)
								}
							},
							backgroundColor = background,
							elevation = 1.dp,
							navigationIcon = {
								IconButton(onClick = {
									coroutineScope.launch {
										scaffoldState.drawerState.open()
									}
								}) {
									Icon(imageVector = Icons.Default.Menu, contentDescription = "드로우어")
								}
							}
						)
					},
					floatingActionButton = {
						FloatingActionButton(
							onClick = {
								val intent = Intent(this, AddBookmarkActivity::class.java)
								startActivity(intent)
							},
							backgroundColor = Color.Black
						) {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "북마크 추가 화면으로 이동",
								tint = Color.White
							)
						}
					},
					drawerContent = {
						Row(
							modifier = modifier
								.fillMaxWidth()
								.background(drawer_header_background)
								.padding(vertical = 24.dp),
							horizontalArrangement = Arrangement.Center,
							verticalAlignment = Alignment.CenterVertically
						) {
							Icon(
								imageVector = Icons.Default.MenuBook,
								contentDescription = "북마크 이미지",
								tint = Color.White
							)

							Text(
								modifier = modifier.padding(start = 8.dp),
								text = "Bookmark Manager",
								fontWeight = FontWeight.Bold,
								color = Color.White,
								fontSize = 20.sp
							)
						}

						LazyColumn(
							modifier = modifier
								.fillMaxSize()
								.padding(24.dp)
						) {
							item {
								Text(
									text = "Group",
									color = Color.White,
									fontSize = 18.sp,
									fontWeight = FontWeight.Bold
								)
							}

							items(items = bookmarksViewModel.bookmarksUiState.groupUiStates) { groupUiState: GroupUiState ->
								Row(
									modifier = modifier
										.fillMaxWidth()
										.clickable {
											bookmarksViewModel.setSelectedGroup(groupUiState.name)
											coroutineScope.launch {
												scaffoldState.drawerState.close()
											}
										}
										.padding(vertical = 24.dp),
									verticalAlignment = Alignment.CenterVertically
								) {
									Icon(
										imageVector = Icons.Default.Bookmarks,
										contentDescription = "북마크 이미지",
										tint = Color.White
									)
									Text(
										modifier = modifier.padding(start = 12.dp),
										text = groupUiState.name,
										color = Color.White,
										fontSize = 16.sp
									)
								}
							}
						}
					},
					drawerBackgroundColor = drawer_body_background,
					scaffoldState = scaffoldState,
					backgroundColor = background
				) { paddingValues ->

					LazyColumn(
						modifier = modifier
							.padding(paddingValues)
							.fillMaxSize(),
						contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
					) {
						items(items = bookmarksUiState.groupedBookmarkUiStates) { bookmarkUiState ->

							Card(
								modifier = modifier
									.fillMaxWidth()
									.padding(vertical = 8.dp),
								shape = RoundedCornerShape(size = 6.dp),
								backgroundColor = Color.White,
								elevation = 2.dp
							) {
								Column(
									modifier = modifier
										.padding(vertical = 16.dp, horizontal = 20.dp)
								) {
									Text(
										text = bookmarkUiState.title,
										color = Color.Black,
										fontWeight = FontWeight.Bold,
										fontSize = 28.sp,
										maxLines = 1,
										overflow = TextOverflow.Ellipsis
									)

									Text(
										text = bookmarkUiState.url,
										color = url_text_color,
										fontSize = 14.sp,
										maxLines = 1,
										overflow = TextOverflow.Ellipsis
									)

									LazyRow(
										modifier = modifier
											.fillMaxWidth()
											.padding(vertical = 16.dp)
									) {
										items(items = bookmarkUiState.tags) { tag ->
											Box(
												modifier = modifier
													.padding(end = 8.dp)
													.clip(RoundedCornerShape(8.dp))
													.background(tag_background)
											) {
												Text(
													modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
													text = tag,
													fontSize = 12.sp
												)
											}
										}
									}

									Text(
										text = bookmarkUiState.description,
										color = Color.Black,
										fontSize = 18.sp,
										maxLines = 3,
										overflow = TextOverflow.Ellipsis
									)
								}
							}
						}
					}
				}
			}
		}
	}
}
