package com.epamupskills.core.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epamupskills.core.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel(), DefaultLifecycleObserver {

    private val _navEvents = Channel<NavigationEvent>()
    val navEvents = _navEvents.receiveAsFlow()

    private val _errorMessage = MutableSharedFlow<String?>(replay = 0)
    val errorMessage = _errorMessage.asSharedFlow()

    private val _hasError = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    protected val loading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = loading

    override fun onCleared() {
        _navEvents.close()
        super.onCleared()
    }

    fun onNavigationEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _navEvents.send(event)
        }
    }

    protected suspend fun <T> Result<T>.renderBaseStateByResult() {
        loading.value = false
        onSuccess {
            _errorMessage.emit(null)
            _hasError.emit(false)
        }.onFailure { error ->
            //todo crashlytics Non fatal error
            _errorMessage.emit(error.message.orEmpty())
            _hasError.emit(true)
        }
    }

    protected fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(context = CoroutineExceptionHandler { _, throwable ->
            //todo crashlytics Non fatal error
        }, block = block)
}