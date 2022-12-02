package com.kldaji.bookmark_manager.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.bookmark_manager.data.entity.UserInfo
import com.kldaji.bookmark_manager.data.repository.LoginRepository
import com.kldaji.bookmark_manager.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginRepository: LoginRepository,
) : ViewModel() {

	var loginUiState by mutableStateOf(LoginUiState())
		private set

	private fun setSignInUserMessage(userMessage: String) {
		loginUiState = loginUiState.copy(signInUserMessage = userMessage)
	}

	private fun setSignUpUserMessage(userMessage: String) {
		loginUiState = loginUiState.copy(signUpUserMessage = userMessage)
	}

	fun hideUserMessage() {
		loginUiState = loginUiState.copy(
			signInUserMessage = null,
			signUpUserMessage = null
		)
	}

	fun setLoginId(id: String) {
		loginUiState = loginUiState.copy(loginId = id)
	}

	fun setLoginPassword(password: String) {
		loginUiState = loginUiState.copy(loginPassword = password)
	}

	fun setNewId(id: String) {
		loginUiState = loginUiState.copy(newId = id)
	}

	fun setNewPassword(password: String) {
		loginUiState = loginUiState.copy(newPassword = password)
	}

	fun setIsPasswordVisible(isPasswordVisible: Boolean) {
		loginUiState = loginUiState.copy(isPasswordVisible = isPasswordVisible)
	}

	fun signIn() {
		if (loginUiState.loginId.isEmpty() || loginUiState.loginId.isBlank()) {
			setSignInUserMessage("please enter id")
			return
		}

		if (loginUiState.loginPassword.isEmpty() || loginUiState.loginPassword.isBlank()) {
			setSignInUserMessage("please enter password")
			return
		}

		viewModelScope.launch {
			val result = loginRepository.signIn(
				UserInfo(
					username = loginUiState.loginId,
					password = loginUiState.loginPassword
				)
			)

			Log.d("LoginViewModel", result.toString())
			when (result) {
				is Result.NetworkError -> {}
				is Result.GenericError -> {}
				is Result.Success -> {
					loginUiState = loginUiState.copy(signInSuccess = Unit)
				}
			}
		}
	}

	fun signUp() {
		if (loginUiState.newId.isEmpty() || loginUiState.newId.isBlank()) {
			setSignUpUserMessage("please enter id")
			return
		}

		if (loginUiState.newPassword.isEmpty() || loginUiState.newPassword.isBlank()) {
			setSignUpUserMessage("please enter password")
			return
		}

		viewModelScope.launch {
			val result = loginRepository.signUp(
				UserInfo(
					username = loginUiState.newId,
					password = loginUiState.newPassword
				)
			)

			when (result) {
				is Result.NetworkError -> {}
				is Result.GenericError -> {}
				is Result.Success -> {
					loginUiState = loginUiState.copy(signUpSuccess = Unit)
				}
			}
		}
	}
}