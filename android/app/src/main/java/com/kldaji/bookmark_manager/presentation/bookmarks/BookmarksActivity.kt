package com.kldaji.bookmark_manager.presentation.bookmarks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.kldaji.bookmark_manager.R
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse
import com.kldaji.bookmark_manager.presentation.addbookmark.AddBookmarkActivity
import com.kldaji.bookmark_manager.presentation.login.LoginActivity
import com.kldaji.bookmark_manager.presentation.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksActivity : ComponentActivity() {

	private val bookmarksViewModel: BookmarksViewModel by viewModels()

	@OptIn(ExperimentalMaterialApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val coroutineScope = rememberCoroutineScope()
				val bookmarksUiState = bookmarksViewModel.bookmarksUiState
				val sheetState = rememberModalBottomSheetState(
					initialValue = ModalBottomSheetValue.Hidden,
					skipHalfExpanded = true
				)
				val bookmarksState = rememberScaffoldState()
				val searchState = rememberScaffoldState()
				val focusManager = LocalFocusManager.current
				val focusRequester: FocusRequester = remember { FocusRequester() }
				var isPlaying by remember { mutableStateOf(true) }
				var speed by remember { mutableStateOf(1f) }
				val compositionLoading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
				val progress by animateLottieCompositionAsState(
					compositionLoading,
					iterations = LottieConstants.IterateForever,
					isPlaying = isPlaying,
					speed = speed,
					restartOnPlay = false
				)

				LaunchedEffect(key1 = sheetState.isVisible) {
					if (sheetState.isVisible.not()) {
						focusManager.clearFocus()
					}
				}

				LaunchedEffect(key1 = bookmarksUiState.bookmarksUserMessage) {
					bookmarksUiState.bookmarksUserMessage?.let {
						bookmarksState.snackbarHostState.showSnackbar(it)
						bookmarksViewModel.hideUserMessage()
					}
				}

				LaunchedEffect(key1 = bookmarksUiState.searchUserMessage) {
					bookmarksUiState.searchUserMessage?.let {
						searchState.snackbarHostState.showSnackbar(it)
						bookmarksViewModel.hideUserMessage()
					}
				}

				ModalBottomSheetLayout(
					sheetContentColor = background,
					sheetState = sheetState,
					sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
					sheetContent = {
						Scaffold(
							modifier = modifier
								.fillMaxWidth()
								.fillMaxHeight(0.95f),
							scaffoldState = searchState,
							topBar = {
								Surface(
									modifier = modifier.fillMaxWidth(),
									elevation = 1.dp
								) {
									OutlinedTextField(
										modifier = modifier
											.padding(horizontal = 16.dp, vertical = 12.dp)
											.focusRequester(focusRequester),
										value = bookmarksUiState.query,
										onValueChange = { bookmarksViewModel.setQuery(it) },
										leadingIcon = {
											IconButton(onClick = {}) {
												Icon(
													imageVector = Icons.Filled.Search,
													contentDescription = "",
												)
											}
										},
										trailingIcon = {
											IconButton(onClick = { bookmarksViewModel.setQuery("") }) {
												if (bookmarksUiState.query.isNotEmpty()) {
													Icon(
														imageVector = Icons.Rounded.Clear,
														contentDescription = "????????? ??????"
													)
												}
											}
										},
										colors = TextFieldDefaults.textFieldColors(
											backgroundColor = Color.White,
											focusedIndicatorColor = drawer_header_background,
											unfocusedIndicatorColor = drawer_body_background
										),
										shape = RoundedCornerShape(36.dp),
										keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
										keyboardActions = KeyboardActions(
											onDone = { focusManager.clearFocus() }
										)
									)
								}
							},
							content = {
								if (bookmarksUiState.queriedBookmarks == null) {
									if (bookmarksUiState.allBookmarks.isEmpty()) {
										Column(
											modifier = modifier
												.padding(it)
												.padding(vertical = 50.dp, horizontal = 16.dp)
												.fillMaxWidth(),
											verticalArrangement = Arrangement.Center,
											horizontalAlignment = Alignment.CenterHorizontally
										) {
											Image(
												painter = painterResource(R.drawable.empty),
												contentDescription = "",
												modifier = modifier.size(150.dp),
											)
										}
									} else {
										LazyColumn(
											modifier = modifier
												.fillMaxSize()
												.padding(it)
												.padding(vertical = 8.dp, horizontal = 16.dp)
										) {
											items(items = bookmarksUiState.allBookmarks) { bookmarkResponse ->
												BookmarkItem(
													modifier,
													bookmarksUiState,
													bookmarkResponse,
													bookmarksViewModel,
													::startActivityEdit,
													bookmarksViewModel::deleteBookmark,
													clickItem = ::startWebView)
											}
										}
									}
								} else if (bookmarksUiState.queriedBookmarks.isEmpty()) {
									Column(
										modifier = modifier
											.padding(it)
											.padding(vertical = 50.dp, horizontal = 16.dp)
											.fillMaxWidth(),
										verticalArrangement = Arrangement.Center,
										horizontalAlignment = Alignment.CenterHorizontally
									) {
										Image(
											painter = painterResource(R.drawable.empty),
											contentDescription = "",
											modifier = modifier.size(150.dp),
										)
									}
								} else {
									LazyColumn(
										modifier = modifier
											.fillMaxSize()
											.padding(it)
											.padding(vertical = 8.dp, horizontal = 16.dp)
									) {
										items(items = bookmarksUiState.queriedBookmarks) { bookmarkResponse ->
											BookmarkItem(
												modifier,
												bookmarksUiState,
												bookmarkResponse,
												bookmarksViewModel,
												::startActivityEdit,
												bookmarksViewModel::deleteBookmark,
												clickItem = ::startWebView)
										}
									}
								}
							}
						)
					}
				) {
					BottomSheetContent(
						modifier = modifier,
						bookmarksUiState = bookmarksUiState,
						coroutineScope = coroutineScope,
						clickFloatingButton = ::startActivity,
						clickEditButton = ::startActivityEdit,
						clickDeleteButton = bookmarksViewModel::deleteBookmark,
						bookmarksViewModel = bookmarksViewModel,
						clickSearchButton = {
							coroutineScope.launch {
								sheetState.show()
							}
						},
						state = bookmarksState,
						startWebView = ::startWebView,
						logout = ::startLogin
					)
				}

				bookmarksUiState.showLoading?.let {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.Black.copy(alpha = 0.3f))
							.pointerInput(Unit) {},
						contentAlignment = Alignment.Center
					) {
						LottieAnimation(
							compositionLoading,
							progress,
							modifier = modifier.size(200.dp)
						)
					}
				}
			}

		}
	}

	override fun onStart() {
		super.onStart()

		bookmarksViewModel.getGroups()
		bookmarksViewModel.getNewBookmarkResponses()
	}

	private fun startActivity() {
		val intent = Intent(this, AddBookmarkActivity::class.java)
		startActivity(intent)
	}

	private fun startActivityEdit(id: String) {
		val intent = Intent(this, AddBookmarkActivity::class.java).apply {
			putExtra("BookmarkId", id)
		}
		startActivity(intent)
	}

	private fun startWebView(url: String) {
		val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
		startActivity(webIntent)
	}

	private fun startLogin() {
		val intent = Intent(this, LoginActivity::class.java)
		startActivity(intent)
		finish()
	}
}

@Composable
fun BottomSheetContent(
	modifier: Modifier,
	bookmarksUiState: BookmarksUiState,
	coroutineScope: CoroutineScope,
	clickFloatingButton: () -> Unit,
	clickEditButton: (id: String) -> Unit,
	clickDeleteButton: (id: String) -> Unit,
	bookmarksViewModel: BookmarksViewModel,
	clickSearchButton: () -> Unit,
	state: ScaffoldState,
	startWebView: (url: String) -> Unit,
	logout: () -> Unit
) {
	Scaffold(
		scaffoldState = state,
		topBar = {
			TopAppBar(
				title = {
					if (bookmarksUiState.selectedGroup.isEmpty()) {
						Text(text = "no group...")
					} else {
						Text(text = bookmarksUiState.selectedGroup)
					}
				},
				actions = {
					IconButton(
						modifier = modifier.padding(end = 6.dp),
						onClick = { clickSearchButton() }
					) {
						Icon(
							imageVector = Icons.Default.Search,
							contentDescription = ""
						)
					}
				},
				backgroundColor = background,
				elevation = 1.dp,
				navigationIcon = {
					IconButton(onClick = {
						coroutineScope.launch {
							state.drawerState.open()
						}
					}) {
						Icon(imageVector = Icons.Default.Menu, contentDescription = "????????????")
					}
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { clickFloatingButton() },
				backgroundColor = Color.Black
			) {
				Icon(
					imageVector = Icons.Default.Add,
					contentDescription = "????????? ?????? ???????????? ??????",
					tint = Color.White
				)
			}
		},
		drawerContent = {
			Column(
				modifier = modifier.fillMaxSize(),
				verticalArrangement = Arrangement.SpaceBetween
			) {
				Column(modifier = modifier.fillMaxWidth()) {
					Row(
						modifier = modifier
							.fillMaxWidth()
							.background(drawer_header_background)
							.padding(vertical = 24.dp),
						horizontalArrangement = Arrangement.Center,
						verticalAlignment = Alignment.CenterVertically
					) {
						Image(
							painterResource(R.drawable.bookmark),
							"",
							modifier = modifier.size(25.dp)
						)

						Text(
							modifier = modifier.padding(start = 12.dp),
							text = "Bookmarks",
							fontWeight = FontWeight.Bold,
							color = Color.White,
							fontSize = 20.sp
						)
					}

					LazyColumn(
						modifier = modifier.padding(24.dp)
					) {
						item {
							Text(
								modifier = modifier.padding(bottom = 16.dp),
								text = "Group",
								color = Color.White,
								fontSize = 18.sp,
								fontWeight = FontWeight.Bold
							)
						}

						items(items = bookmarksViewModel.bookmarksUiState.groups) { group: String ->
							Row(
								modifier = modifier
									.fillMaxWidth()
									.clickable {
										bookmarksViewModel.setSelectedGroup(group)
										coroutineScope.launch {
											state.drawerState.close()
										}
									}
									.padding(vertical = 12.dp),
								verticalAlignment = Alignment.CenterVertically
							) {
								Icon(
									imageVector = Icons.Default.Bookmarks,
									contentDescription = "????????? ?????????",
									tint = Color.White
								)
								Text(
									modifier = modifier.padding(start = 12.dp),
									text = group,
									color = Color.White,
									fontSize = 16.sp
								)
							}
						}
					}
				}

				Row(
					modifier = modifier
						.fillMaxWidth()
						.padding(vertical = 16.dp, horizontal = 24.dp)
						.clickable { logout() },
					verticalAlignment = Alignment.CenterVertically,
				) {
					Icon(modifier = modifier.padding(end = 16.dp), imageVector = Icons.Default.Logout, contentDescription = "", tint = Color.White)

					Text(text = "LOGOUT", color = Color.White)
				}
			}

		},
		drawerBackgroundColor = drawer_body_background,
		backgroundColor = background
	) { paddingValues ->

		if (bookmarksUiState.bookmarkResponses.isEmpty()) {
			Column(
				modifier = modifier
					.padding(paddingValues)
					.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Image(
					modifier = modifier.padding(bottom = 50.dp),
					painter = painterResource(R.drawable.empty),
					contentDescription = "",
				)
			}
		} else {
			LazyColumn(
				modifier = modifier
					.padding(paddingValues)
					.fillMaxSize(),
				contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
			) {
				items(items = bookmarksUiState.bookmarkResponses) { bookmarkResponse ->
					BookmarkItem(modifier, bookmarksUiState, bookmarkResponse, bookmarksViewModel, clickEditButton, clickDeleteButton, clickItem = startWebView)
				}
			}
		}
	}
}

@Composable
fun BookmarkItem(
	modifier: Modifier,
	bookmarksUiState: BookmarksUiState,
	bookmarkResponse: BookmarkResponse,
	bookmarksViewModel: BookmarksViewModel,
	clickEditButton: (id: String) -> Unit,
	clickDeleteButton: (id: String) -> Unit,
	clickItem: (url: String) -> Unit
) {
	Column(modifier = modifier.fillMaxWidth()) {
		Column(
			modifier = modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp)
				.shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
				.clip(RoundedCornerShape(12.dp))
				.background(color = Color.White)
				.animateContentSize(spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
		) {

			if (bookmarksUiState.selectedBookmarkId != bookmarkResponse.id) {
				Row(
					modifier = modifier
						.fillMaxWidth()
						.clickable {
							bookmarksViewModel.setSelectedBookmarkId(bookmarkResponse.id)
						},
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						modifier = modifier
							.padding(start = 12.dp, end = 6.dp, top = 16.dp, bottom = 16.dp)
							.weight(0.9f),
						text = bookmarkResponse.title,
						color = Color.Black,
						fontWeight = FontWeight.Bold,
						fontSize = 16.sp,
					)

					Icon(
						modifier = modifier
							.padding(end = 4.dp)
							.weight(0.1f),
						imageVector = Icons.Default.ExpandMore,
						contentDescription = ""
					)
				}
			} else {
				Row(
					modifier = modifier
						.fillMaxWidth()
						.clickable {
							bookmarksViewModel.setSelectedBookmarkId("")
						}
						.drawBehind {
							val strokeWidth = ((0.5).dp * density).toPx()
							val y = size.height - strokeWidth / 2

							drawLine(
								color = url_text_color,
								Offset(0f, y),
								Offset(size.width, y),
								strokeWidth = strokeWidth
							)
						},
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						modifier = modifier
							.padding(start = 12.dp, end = 6.dp, top = 16.dp, bottom = 16.dp)
							.weight(0.9f),
						text = bookmarkResponse.title,
						color = Color.Black,
						fontWeight = FontWeight.Bold,
						fontSize = 16.sp,
					)

					Icon(
						modifier = modifier
							.padding(end = 4.dp)
							.weight(0.1f),
						imageVector = Icons.Default.ExpandLess,
						contentDescription = ""
					)
				}

				Column(
					modifier = modifier
						.fillMaxWidth()
						.clickable {
							clickItem(bookmarkResponse.url)
						}
				) {
					Text(
						modifier = modifier.padding(horizontal = 12.dp, vertical = 16.dp),
						text = bookmarkResponse.url,
						color = url_text_color,
						fontSize = 12.sp,
					)

					LazyRow(
						modifier = modifier
							.fillMaxWidth()
							.padding(horizontal = 12.dp),
					) {
						items(items = bookmarkResponse.tags) { tag ->
							Box(
								modifier = modifier
									.padding(end = 8.dp)
									.clip(RoundedCornerShape(8.dp))
									.background(tag_background)
							) {
								Text(
									modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
									text = tag,
									fontSize = 11.sp
								)
							}
						}
					}

					Text(
						modifier = modifier.padding(horizontal = 12.dp, vertical = 16.dp),
						text = bookmarkResponse.summary,
						color = Color.Black,
						fontSize = 16.sp,
					)
				}
			}
		}

		if (bookmarksUiState.selectedBookmarkId == bookmarkResponse.id) {
			Row(
				modifier = modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly,
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(
					modifier = modifier.clip(CircleShape),
					onClick = {
						clickEditButton(bookmarkResponse.id)
					}
				) {
					Icon(imageVector = Icons.Default.Edit, contentDescription = "")
				}
				IconButton(
					modifier = modifier.clip(CircleShape),
					onClick = { clickDeleteButton(bookmarkResponse.id) }
				) {
					Icon(imageVector = Icons.Default.Delete, contentDescription = "")
				}
			}
		}
	}
}
