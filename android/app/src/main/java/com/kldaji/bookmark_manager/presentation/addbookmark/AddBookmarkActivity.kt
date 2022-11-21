package com.kldaji.bookmark_manager.presentation.addbookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarksActivity
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme
import com.kldaji.bookmark_manager.presentation.theme.background
import com.kldaji.bookmark_manager.presentation.theme.tag_background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookmarkActivity : ComponentActivity() {

	private lateinit var callback: OnBackPressedCallback

	@OptIn(ExperimentalMaterialApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val addBookmarkViewModel: AddBookmarkViewModel by viewModels()

		intent.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
			addBookmarkViewModel.setBookmarkResponse(url)
		}

		callback = object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				startBookmarkActivity()
			}
		}
		onBackPressedDispatcher.addCallback(this, callback)

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val addBookmarkUiState = addBookmarkViewModel.addBookmarkUiState
				val scaffoldState = rememberScaffoldState()

				LaunchedEffect(key1 = addBookmarkUiState.bookmarkResponse) {
					addBookmarkUiState.bookmarkResponse?.let {
						addBookmarkViewModel.setTitle(TextFieldValue(it.title))
						addBookmarkViewModel.setDescription(TextFieldValue(it.summary))
						addBookmarkViewModel.addTags(it.topics)
					}
				}

				LaunchedEffect(key1 = addBookmarkUiState.isDuplicatedTag) {
					if (addBookmarkUiState.isDuplicatedTag) {
						scaffoldState.snackbarHostState.showSnackbar("중복된 태그입니다.")
						addBookmarkViewModel.doneDuplicatedTag()
					}
				}

				LaunchedEffect(key1 = addBookmarkUiState.isDuplicatedGroup) {
					if (addBookmarkUiState.isDuplicatedGroup) {
						scaffoldState.snackbarHostState.showSnackbar("중복된 그룹입니다.")
						addBookmarkViewModel.doneDuplicatedGroup()
					}
				}

				Scaffold(
					topBar = {
						TopAppBar(
							title = { Text(text = "Add Bookmark") },
							backgroundColor = background,
							navigationIcon = {
								IconButton(onClick = { startBookmarkActivity() }) {
									Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "북마크 리스트 화면으로 이동")
								}
							}
						)
					},
					scaffoldState = scaffoldState,
					backgroundColor = background
				) { paddingValues ->

					if (addBookmarkUiState.isShowAddGroupDialog) {
						AddDialog(
							modifier = modifier,
							onDismissRequest = { addBookmarkViewModel.hideAddGroupDialog() },
							textFieldValue = addBookmarkUiState.newGroup,
							textFieldValueOnChange = { addBookmarkViewModel.setNewGroup(it) },
							label = "GROUP",
							onCancel = { addBookmarkViewModel.hideAddGroupDialog() },
							onConfirm = {
								addBookmarkViewModel.hideAddGroupDialog()
								addBookmarkViewModel.addGroup(addBookmarkUiState.newGroup.text)
							}
						)
					}

					if (addBookmarkUiState.isShowProgressBar) {
						Box(modifier = Modifier
							.fillMaxSize()
							.clickable(
								indication = null, // disable ripple effect
								interactionSource = remember { MutableInteractionSource() },
								onClick = { }
							)
							.background(color = Color.Black.copy(alpha = 0.3f)),
							contentAlignment = Alignment.Center
						) {
							CircularProgressIndicator()
						}
					}

					Column(
						modifier = modifier
							.fillMaxSize()
							.padding(paddingValues)
							.padding(horizontal = 28.dp, vertical = 16.dp),
						verticalArrangement = Arrangement.SpaceBetween,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Column {
							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(bottom = 12.dp),
								value = addBookmarkUiState.url,
								onValueChange = { addBookmarkViewModel.setUrl(it) },
								label = { Text(text = "URL") },
								maxLines = 1,
								singleLine = true,
								colors = TextFieldDefaults.textFieldColors(
									backgroundColor = Color.White,
									focusedIndicatorColor = Color.Black,
									focusedLabelColor = Color.Black
								)
							)

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(bottom = 12.dp),
								value = addBookmarkUiState.title,
								onValueChange = { addBookmarkViewModel.setTitle(it) },
								label = { Text(text = "TITLE") },
								maxLines = 1,
								singleLine = true,
								colors = TextFieldDefaults.textFieldColors(
									backgroundColor = Color.White,
									focusedIndicatorColor = Color.Black,
									focusedLabelColor = Color.Black
								)
							)

							ExposedDropdownMenuBox(
								modifier = modifier
									.fillMaxWidth()
									.padding(bottom = 12.dp),
								expanded = addBookmarkUiState.isShowGroups,
								onExpandedChange = {
									if (it) addBookmarkViewModel.showGroups()
									else addBookmarkViewModel.hideGroups()
								}
							) {
								OutlinedTextField(
									modifier = modifier.fillMaxWidth(),
									readOnly = true,
									label = { Text(text = "GROUP") },
									value = addBookmarkUiState.selectedGroup,
									onValueChange = {},
									trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = addBookmarkUiState.isShowGroups) },
									colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
										backgroundColor = Color.White,
										focusedLabelColor = Color.Black,
										focusedBorderColor = Color.Black
									)
								)

								ExposedDropdownMenu(
									expanded = addBookmarkUiState.isShowGroups,
									onDismissRequest = { addBookmarkViewModel.hideGroups() }
								) {
									addBookmarkUiState.groupUiStates.forEach { groupUiState ->
										DropdownMenuItem(
											onClick = {
												addBookmarkViewModel.setSelectedGroup(groupUiState.name)
												addBookmarkViewModel.hideGroups()
											}
										) {
											Text(text = groupUiState.name)
										}
									}
									DropdownMenuItem(onClick = { addBookmarkViewModel.showAddGroupDialog() }) {
										Row {
											Icon(imageVector = Icons.Default.AddCircle, contentDescription = "그룹 추가")
											Text(
												modifier = modifier.padding(start = 4.dp),
												text = "그룹 추가"
											)
										}
									}
								}
							}

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(bottom = 12.dp),
								value = addBookmarkUiState.newTag,
								onValueChange = { addBookmarkViewModel.setNewTag(it) },
								label = { Text(text = "TAG") },
								maxLines = 1,
								singleLine = true,
								colors = TextFieldDefaults.textFieldColors(
									backgroundColor = Color.White,
									focusedIndicatorColor = Color.Black,
									focusedLabelColor = Color.Black
								),
								keyboardActions = KeyboardActions(onDone = {
									addBookmarkViewModel.addTag(addBookmarkUiState.newTag)
									addBookmarkViewModel.setNewTag("")
								})
							)

							LazyRow(
								modifier = modifier
									.fillMaxWidth()
									.padding(start = 6.dp, bottom = 12.dp)
							) {
								items(items = addBookmarkUiState.tags) { tag ->
									Box(
										modifier = modifier
											.padding(end = 8.dp)
											.clip(RoundedCornerShape(8.dp))
											.background(tag_background)
											.clickable { addBookmarkViewModel.removeTag(tag) }
									) {
										Text(
											modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
											text = tag,
											fontSize = 14.sp
										)
									}
								}
							}

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.height(200.dp)
									.padding(vertical = 8.dp),
								value = addBookmarkUiState.description,
								onValueChange = { addBookmarkViewModel.setDescription(it) },
								label = { Text(text = "DESCRIPTION") },
								colors = TextFieldDefaults.textFieldColors(
									backgroundColor = Color.White,
									focusedIndicatorColor = Color.Black,
									focusedLabelColor = Color.Black
								)
							)
						}

						Button(
							modifier = modifier
								.width(250.dp)
								.padding(horizontal = 24.dp),
							onClick = {
								addBookmarkViewModel.addBookmarkUiState()
								startBookmarkActivity()
							},
							colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
							enabled = true
						) {
							Text(
								text = "DONE",
								fontSize = 16.sp,
								color = Color.White
							)
						}
					}
				}
			}
		}
	}

	@Composable
	fun AddDialog(
		modifier: Modifier,
		onDismissRequest: () -> Unit,
		textFieldValue: TextFieldValue,
		textFieldValueOnChange: (TextFieldValue) -> Unit,
		label: String,
		onCancel: () -> Unit,
		onConfirm: () -> Unit
	) {
		Dialog(onDismissRequest = { onDismissRequest() }) {
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
					value = textFieldValue,
					onValueChange = { textFieldValueOnChange(it) },
					label = { Text(text = label) },
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
						onClick = { onCancel() },
						colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
					) {
						Text(text = "취소")
					}

					Button(
						onClick = { onConfirm() },
						colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
					) {
						Text(text = "추가")
					}
				}
			}
		}
	}

	private fun startBookmarkActivity(intent: Intent = Intent(this@AddBookmarkActivity, BookmarksActivity::class.java)) {
		startActivity(intent)
		finish()
	}

	override fun onDestroy() {
		super.onDestroy()

		callback.remove()
	}
}
