package com.kldaji.bookmark_manager.presentation.login

data class LoginUiState(
	val signInUserMessage: String? = null,
	val signUpUserMessage: String? = null,
	val loginId: String = "",
	val loginPassword: String = "",
	val newId: String = "",
	val newPassword: String = "",
	val isPasswordVisible: Boolean = false,
	val signInSuccess: Unit? = null,
	val signUpSuccess: Unit? = null,
)