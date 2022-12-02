package com.kldaji.bookmark_manager.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kldaji.bookmark_manager.presentation.bookmarks.BookmarksActivity
import com.kldaji.bookmark_manager.presentation.theme.BookmarkmanagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

	private val loginViewModel: LoginViewModel by viewModels()

	@OptIn(ExperimentalMaterialApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			BookmarkmanagerTheme {
				val modifier = Modifier
				val coroutineScope = rememberCoroutineScope()
				val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
				val uiState = loginViewModel.loginUiState
				val signInState = rememberScaffoldState()
				val signUpState = rememberScaffoldState()

				LaunchedEffect(key1 = uiState.signInSuccess) {
					uiState.signInSuccess?.let {
						startActivity(Intent(this@LoginActivity, BookmarksActivity::class.java))
					}
				}

				LaunchedEffect(key1 = uiState.signInUserMessage) {
					uiState.signInUserMessage?.let {
						signInState.snackbarHostState.showSnackbar(it)
						loginViewModel.hideUserMessage()
					}
				}

				LaunchedEffect(key1 = uiState.signUpUserMessage) {
					uiState.signUpUserMessage?.let {
						signUpState.snackbarHostState.showSnackbar(it)
						loginViewModel.hideUserMessage()
					}
				}

				ModalBottomSheetLayout(
					sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
					sheetState = sheetState,
					sheetContent = {
						Scaffold(
							scaffoldState = signUpState,
							modifier = modifier.fillMaxSize()
						) {

							UserInfo(
								modifier = modifier.padding(it),
								isSignUp = true,
								uiState = uiState,
								viewModel = loginViewModel,
								onButtonClick = {
									loginViewModel.signUp()
								},
								onCreateAccountClick = {}
							)
						}
					}
				) {
					Scaffold(
						scaffoldState = signInState,
						modifier = modifier.fillMaxSize()
					) {

						UserInfo(
							modifier = modifier.padding(it),
							isSignUp = false,
							uiState = uiState,
							viewModel = loginViewModel,
							onButtonClick = {
								loginViewModel.signIn()
							},
							onCreateAccountClick = {
								coroutineScope.launch {
									sheetState.show()
								}
							}
						)
					}
				}
			}
		}
	}
}

@Composable
fun UserInfo(
	modifier: Modifier,
	isSignUp: Boolean,
	uiState: LoginUiState,
	viewModel: LoginViewModel,
	onButtonClick: () -> Unit,
	onCreateAccountClick: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(horizontal = 24.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		OutlinedTextField(
			modifier = modifier.fillMaxWidth(),
			value = if (isSignUp) uiState.newId else uiState.loginId,
			onValueChange = { if (isSignUp) viewModel.setNewId(it) else viewModel.setLoginId(it) },
			label = { Text(text = "ID") },
			maxLines = 1,
			singleLine = true,
			colors = TextFieldDefaults.textFieldColors(
				backgroundColor = Color.White,
				focusedIndicatorColor = Color.Black,
				focusedLabelColor = Color.Black,
				textColor = Color.Black
			),
			leadingIcon = {
				Icon(imageVector = Icons.Default.Person, contentDescription = "")
			},
			placeholder = { Text("Please enter id") },
		)

		OutlinedTextField(
			modifier = modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			value = if (isSignUp) uiState.newPassword else uiState.loginPassword,
			onValueChange = { if (isSignUp) viewModel.setNewPassword(it) else viewModel.setLoginPassword(it) },
			label = { Text(text = "PASSWORD") },
			maxLines = 1,
			singleLine = true,
			colors = TextFieldDefaults.textFieldColors(
				backgroundColor = Color.White,
				focusedIndicatorColor = Color.Black,
				focusedLabelColor = Color.Black,
				textColor = Color.Black
			),
			leadingIcon = {
				Icon(imageVector = Icons.Default.Key, contentDescription = "")
			},
			placeholder = { Text("Please enter password") },
			visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
			trailingIcon = {
				val imageVector = if (uiState.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

				IconButton(onClick = { viewModel.setIsPasswordVisible(!uiState.isPasswordVisible) }) {
					Icon(imageVector = imageVector, "")
				}
			}
		)

		Button(
			modifier = modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			onClick = { onButtonClick() },
		) {
			val buttonText = if (isSignUp) "SIGN UP" else "SIGN IN"
			Text(text = buttonText)
		}

		if (!isSignUp) {
			Text(
				modifier = modifier
					.padding(16.dp)
					.clickable { onCreateAccountClick() },
				text = "CREATE ACCOUNT"
			)
		}
	}
}