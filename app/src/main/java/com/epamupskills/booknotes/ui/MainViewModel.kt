package com.epamupskills.booknotes.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.authorization.domain.usecases.CheckAuthUseCase
import com.epamupskills.booknotes.core.Navigate
import com.epamupskills.booknotes.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
) : BaseViewModel() {

    private val _isAuth = Channel<Boolean>()
    val isAuth = _isAuth.receiveAsFlow()

    init {
        viewModelScope.launch {
            checkAuthUseCase.invoke()
                .onSuccess { authFlow ->
                    authFlow.collect { isAuthorized ->
                        Log.d("MainViewModel", "isAuthorized: $isAuthorized")
                        _isAuth.send(isAuthorized)
                        onChangeDestination(isAuthorized)
                    }
                }.renderBaseStateByResult()
        }
    }

    private fun onChangeDestination(isAuthorized: Boolean) {
        val destination = if (isAuthorized) R.id.to_main else R.id.to_authorization
        onNavigationEvent(Navigate(destination))
    }
}