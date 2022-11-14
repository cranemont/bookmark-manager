package com.kldaji.bookmark_manager.presentation.addbookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarksActivity
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme
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
							backgroundColor = Color.White,
							navigationIcon = {
								IconButton(onClick = { startBookmarkActivity() }) {
									Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "북마크 리스트 화면으로 이동")
								}
							}
						)
					},
					scaffoldState = scaffoldState
				) { paddingValues ->

					if (addBookmarkUiState.isShowAddTagDialog) {
						AddDialog(
							modifier = modifier,
							onDismissRequest = {
								addBookmarkViewModel.hideAddTagDialog()
								addBookmarkViewModel.setNewTag(TextFieldValue(""))
							},
							textFieldValue = addBookmarkUiState.newTag,
							textFileValueOnChange = { addBookmarkViewModel.setNewTag(it) },
							label = "TAG",
							onCancel = {
								addBookmarkViewModel.hideAddTagDialog()
								addBookmarkViewModel.setNewTag(TextFieldValue(""))
							},
							onConfirm = {
								addBookmarkViewModel.hideAddTagDialog()
								addBookmarkViewModel.addTag(addBookmarkUiState.newTag.text)
								addBookmarkViewModel.setNewTag(TextFieldValue(""))
							}
						)
					}

					if (addBookmarkUiState.isShowAddGroupDialog) {
						AddDialog(
							modifier = modifier,
							onDismissRequest = {
								addBookmarkViewModel.hideAddGroupDialog()
								addBookmarkViewModel.setNewGroup(TextFieldValue(""))
							},
							textFieldValue = addBookmarkUiState.newGroup,
							textFileValueOnChange = { addBookmarkViewModel.setNewGroup(it) },
							label = "GROUP",
							onCancel = {
								addBookmarkViewModel.hideAddGroupDialog()
								addBookmarkViewModel.setNewGroup(TextFieldValue(""))
							},
							onConfirm = {
								addBookmarkViewModel.hideAddGroupDialog()
								addBookmarkViewModel.addGroup(addBookmarkUiState.newGroup.text)
								addBookmarkViewModel.setNewGroup(TextFieldValue(""))
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
							.padding(horizontal = 16.dp, vertical = 16.dp),
						verticalArrangement = Arrangement.SpaceBetween,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Column {
							Row(
								modifier = modifier.padding(start = 6.dp, bottom = 6.dp),
								verticalAlignment = Alignment.CenterVertically
							) {
								Text(
									text = "TAGS",
									fontSize = 16.sp,
									color = Color.Black
								)
								Row(
									modifier = modifier
										.padding(start = 16.dp)
										.clickable { addBookmarkViewModel.showAddTagDialog() },
									verticalAlignment = Alignment.CenterVertically
								) {
									Text(
										text = "ADD TAG",
										color = Color.Blue,
										fontSize = 12.sp,
										fontWeight = FontWeight.Bold
									)
								}
							}

							LazyRow(
								modifier = modifier
									.fillMaxWidth()
									.border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
							) {
								if (addBookmarkUiState.tags.isEmpty()) {
									item {
										Column(
											modifier = modifier
												.padding(8.dp),
											verticalArrangement = Arrangement.Center
										) {
											Text(
												modifier = modifier.padding(8.dp),
												text = "please add tags",
												color = Color.LightGray
											)
										}
									}
								} else {
									items(items = addBookmarkUiState.tags) { tag ->
										Chip(
											modifier = modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
											onClick = { addBookmarkViewModel.removeTag(tag) },
											leadingIcon = {
												Icon(
													Icons.Default.Check,
													contentDescription = "태그 선택"
												)
											},
											colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.secondary)
										) {

											Text(
												modifier = modifier.padding(end = 4.dp),
												text = tag
											)
										}
									}
								}
							}

							ExposedDropdownMenuBox(
								modifier = modifier
									.fillMaxWidth()
									.padding(top = 24.dp),
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
									colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
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
											Text(text = "그룹 추가")
										}
									}
								}
							}

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(top = 16.dp, bottom = 8.dp),
								value = addBookmarkUiState.url,
								onValueChange = { addBookmarkViewModel.setUrl(it) },
								label = { Text(text = "URL") },
								maxLines = 1,
								singleLine = true
							)

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.padding(vertical = 8.dp),
								value = addBookmarkUiState.title,
								onValueChange = { addBookmarkViewModel.setTitle(it) },
								label = { Text(text = "TITLE") },
								maxLines = 1,
								singleLine = true
							)

							OutlinedTextField(
								modifier = modifier
									.fillMaxWidth()
									.height(200.dp)
									.padding(vertical = 8.dp),
								value = addBookmarkUiState.description,
								onValueChange = { addBookmarkViewModel.setDescription(it) },
								label = { Text(text = "DESCRIPTION") },
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

	@Composable
	fun AddDialog(
		modifier: Modifier,
		onDismissRequest: () -> Unit,
		textFieldValue: TextFieldValue,
		textFileValueOnChange: (TextFieldValue) -> Unit,
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
					onValueChange = { textFileValueOnChange(it) },
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
