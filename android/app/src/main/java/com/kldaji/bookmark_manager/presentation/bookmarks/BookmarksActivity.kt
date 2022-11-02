package com.kldaji.bookmark_manager.presentation.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kldaji.bookmark_manager.presentation.BookmarksViewModel
import com.kldaji.bookmark_manager.presentation.addbookmark.AddBookmarkActivity
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksActivity : ComponentActivity() {

	companion object {
		const val TAG = "BookmarksActivity"
	}

	@OptIn(ExperimentalPagerApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val bookmarksViewModel: BookmarksViewModel by viewModels()
		val tags = bookmarksViewModel.tags
		val bookmarks = bookmarksViewModel.bookmarks
		bookmarks.forEach {
			Log.d(TAG, it.toString())
		}

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val pagerState = rememberPagerState()
				val coroutineScope = rememberCoroutineScope()

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
						FloatingActionButton(onClick = {
							val intent = Intent(this, AddBookmarkActivity::class.java)
							startActivity(intent)
						}) {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "북마크 추가 화면으로 이동"
							)
						}
					}
				) { paddingValues ->

					Column(modifier = modifier.padding(paddingValues)) {
						ScrollableTabRow(
							selectedTabIndex = pagerState.currentPage,
							indicator = { tabPositions ->
								TabRowDefaults.Indicator(modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
							},
							backgroundColor = Color.White,
							edgePadding = 0.dp
						) {
							tags.forEachIndexed { index, title ->
								Tab(
									text = { Text(text = title) },
									selected = pagerState.currentPage == index,
									onClick = {
										coroutineScope.launch {
											pagerState.scrollToPage(index)
										}
									}
								)
							}
						}

						HorizontalPager(
							modifier = modifier.fillMaxSize(),
							count = tags.size,
							state = pagerState
						) { pageIndex ->

							LazyColumn(
								modifier = modifier.fillMaxSize(),
								contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
							) {
								items(
									items = bookmarks[pageIndex],
									key = { bookmarkUiState ->
										bookmarkUiState.id
									}
								) { bookmarkUiState ->

									Card(
										modifier = modifier
											.fillMaxWidth()
											.padding(vertical = 8.dp),
										shape = RoundedCornerShape(size = 18.dp),
										backgroundColor = Color.LightGray
									) {
										Column(
											modifier = modifier
												.padding(vertical = 16.dp, horizontal = 20.dp)
										) {
											Text(
												text = bookmarkUiState.title,
												color = Color.Black,
												fontWeight = FontWeight.Bold,
												fontSize = 20.sp,
												maxLines = 1,
												overflow = TextOverflow.Ellipsis
											)
											Text(
												modifier = modifier.padding(bottom = 16.dp),
												text = bookmarkUiState.url,
												color = Color.Gray,
												fontSize = 14.sp,
												maxLines = 1,
												overflow = TextOverflow.Ellipsis
											)
											Text(
												text = bookmarkUiState.description,
												color = Color.Black,
												fontSize = 14.sp,
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
	}
}
