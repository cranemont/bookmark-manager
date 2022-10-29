package com.kldaji.bookmark_manager.ui.bookmarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kldaji.bookmark_manager.ui.theme.BookmarkmanagerTheme
import kotlinx.coroutines.launch

class BookmarksActivity : ComponentActivity() {

	@OptIn(ExperimentalPagerApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val pagerState = rememberPagerState()
				val coroutineScope = rememberCoroutineScope()
				val pages = listOf("ALL", "SPORTS", "COMPUTER SCIENCE", "YOUTUBE")

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

					Column(modifier = modifier.padding(paddingValues)) {
						ScrollableTabRow(
							selectedTabIndex = pagerState.currentPage,
							indicator = { tabPositions ->
								TabRowDefaults.Indicator(modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
							},
							backgroundColor = Color.White,
							edgePadding = 0.dp
						) {
							pages.forEachIndexed { index, title ->
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
							count = pages.size,
							state = pagerState
						) { page ->
							Text(
								modifier = modifier
									.fillMaxSize()
									.background(Color.Gray),
								text = page.toString()
							)
						}
					}
				}
			}
		}
	}
}
