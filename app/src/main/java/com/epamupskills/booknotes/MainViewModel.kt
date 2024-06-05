package com.epamupskills.booknotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.epamupskills.authorization.domain.usecases.CheckAuthUseCase
import com.epamupskills.authorization.domain.usecases.SaveCurrentUserIdUseCase
import com.epamupskills.core.Navigate
import com.epamupskills.core.base.BaseViewModel
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
                .onSuccess { result ->
                    result.collect { isAuthorized ->
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