package com.epamupskills.booknotes.authorization.presentation.profile

import androidx.lifecycle.viewModelScope
import com.epamupskills.booknotes.authorization.domain.interactors.ProfileInteractor
import com.epamupskills.booknotes.authorization.presentation.auth_shared.AuthUserIntent
import com.epamupskills.booknotes.authorization.presentation.auth_shared.Confirm
import com.epamupskills.booknotes.authorization.presentation.auth_shared.DeleteAccount
import com.epamupskills.booknotes.authorization.presentation.auth_shared.SignOut
import com.epamupskills.booknotes.core.NavigateTo
import com.epamupskills.booknotes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor,
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProfileViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            profileInteractor.getUserEmail()
                .onSuccess { email ->
                    _state.value = _state.value.copy(email = email)
                }
                .renderBaseStateByResult()
        }
    }

    fun onIntent(intent: AuthUserIntent) {
        when (intent) {
            is Confirm -> onNavigationEvent(NavigateTo(ProfileFragmentDirections.actionProfileFragmentToConfirmDialog()))
            is DeleteAccount -> onAuthChanged(profileInteractor::deleteAccount)
            is SignOut -> onAuthChanged(profileInteractor::signOut)
            else -> Unit
        }
    }

    private fun onAuthChanged(block: suspend () -> Result<Unit>) {
        loading.value = true
        viewModelScope.launch {
            block.invoke().renderBaseStateByResult()
        }
    }
}