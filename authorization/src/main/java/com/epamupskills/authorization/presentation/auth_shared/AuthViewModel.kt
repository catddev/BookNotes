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
            else -> onInputChanged(intent)
        }
    }

    private fun <T> onAuthChanged(block: suspend (UserCredentials) -> Result<T>) {
        onLoading()
        viewModelScope.launch {
            block.invoke(mapper.transform(_state.value.userCredentials)).onResult()
        }
    }

    private fun onInputChanged(intent: AuthUserIntent) {
        _state.update {
            when (intent) {
                is EnterEmail -> it.copy(userCredentials = it.userCredentials.copy(email = intent.email))
                is EnterPassword -> it.copy(userCredentials = it.userCredentials.copy(password = intent.password))
                is ConfirmPassword -> it.copy(confirmedPassword = intent.passwordConfirmed)
                else -> throw UnsupportedOperationException()
            }
        }
        onValidateUserCredentials()
    }

    private fun onValidateUserCredentials() {
        _state.update {
            it.copy(
                isSignInButtonEnabled = validator.invoke(mapper.transform(it.userCredentials)),
                isSignUpButtonEnabled = validator.invoke(
                    mapper.transform(it.userCredentials),
                    it.confirmedPassword
                )
            )
        }
    }
}