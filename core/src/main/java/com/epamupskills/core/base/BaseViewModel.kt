package com.epamupskills.core.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epamupskills.core.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel(), DefaultLifecycleObserver {

    private val _navEvents = Channel<NavigationEvent>()
    val navEvents = _navEvents.receiveAsFlow()

    private val _errorState = MutableSharedFlow<BaseErrorState>(replay = 0)
    val errorState = _errorState.asSharedFlow()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCleared() {
        _navEvents.close()
        super.onCleared()
    }

    fun onNavigationEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _navEvents.send(event)
        }
    }

    protected fun onLoading() {
        _isLoading.value = true
    }

    protected suspend fun <T> Result<T>.onResult() {
        _isLoading.value = false
        onSuccess {
            _errorState.emit(BaseErrorState(hasError = false, errorMessage = ""))
        }.onFailure { error ->
            _errorState.emit(
                BaseErrorState(
                    hasError = true,
                    errorMessage = error.message.orEmpty()
                )
            )
        }
    }
}