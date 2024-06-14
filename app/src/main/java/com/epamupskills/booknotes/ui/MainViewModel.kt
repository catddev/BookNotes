package com.epamupskills.booknotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.authorization.domain.usecases.CheckAuthUseCase
import com.epamupskills.booknotes.authorization.domain.usecases.SaveCurrentUserIdUseCase
import com.epamupskills.booknotes.core.base.BaseViewModel
import com.epamupskills.booknotes.core.Navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val saveCurrentUserIdUseCase: SaveCurrentUserIdUseCase,
) : BaseViewModel() {

    private val _isAuth = MutableLiveData(false)
    val isAuth: LiveData<Boolean> get() = _isAuth

    init {
        viewModelScope.launch {
            checkAuthUseCase.invoke()
                .onSuccess { authFlow ->
                    authFlow.collect { isAuthorized ->
                        _isAuth.value = isAuthorized
                        onChangeDestination(isAuthorized)
                        saveCurrentUserIdUseCase.invoke()
                    }
                }.renderBaseStateByResult()
        }
    }

    private fun onChangeDestination(isAuthorized: Boolean) {
        val destination = if (isAuthorized) R.id.to_main else R.id.to_authorization
        onNavigationEvent(Navigate(destination))
    }
}