package com.epamupskills.authorization.presentation.auth_shared

import androidx.lifecycle.viewModelScope
import com.epamupskills.authorization.domain.interactors.AuthenticationInteractor
import com.epamupskills.authorization.domain.UserCredentialsValidator
import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.authorization.presentation.UserCredentialsUiMapper
import com.epamupskills.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthenticationInteractor,
    private val validator: UserCredentialsValidator,
    private val mapper: UserCredentialsUiMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow(AuthViewState())
    val state = _state.asStateFlow()

    fun onIntent(intent: AuthUserIntent) {
        when (intent) {
            is SignUp -> onAuthChanged(interactor::signUp)
            is SignIn -> onAuthChanged(interactor::signIn)
            is EnterEmail -> onEmailChanged(intent.email)
            is EnterPassword -> onPasswordChanged(intent.password)
            is ConfirmPassword -> onPasswordConfirmationChanged(intent.passwordConfirmed)
            else -> throw UnsupportedOperationException()
        }
    }

    private fun <T> onAuthChanged(block: suspend (UserCredentials) -> Result<T>) {
        onLoading()
        viewModelScope.launch {
            block.invoke(mapper.transform(_state.value.userCredentials)).onResult()
        }
    }

    private fun onEmailChanged(input: String) {
        _state.update {
            it.copy(
                userCredentials = it.userCredentials.copy(email = input),
                isEmailValid = validator.validateEmail(input)
            )
        }
        onValidateUserCredentials()
    }

    private fun onPasswordChanged(input: String) {
        _state.update {
            it.copy(
                userCredentials = it.userCredentials.copy(password = input),
                isPasswordValid = validator.validatePassword(input)
            )
        }
        onValidateUserCredentials()
    }

    private fun onPasswordConfirmationChanged(input: String) {
        _state.update {
            it.copy(
                confirmedPassword = input,
                isPasswordConfirmedCorrectly = validator.validatePasswordConfirmation(
                    it.userCredentials.password,
                    input
                )
            )
        }
        onValidateUserCredentials()
    }

    private fun onValidateUserCredentials() {
        _state.update {
            it.copy(
                isSignInButtonEnabled = it.isEmailValid && it.isPasswordValid,
                isSignUpButtonEnabled = it.isEmailValid && it.isPasswordValid && it.isPasswordConfirmedCorrectly
            )
        }
    }
}