package com.example.exampleproject.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UI_STATE, INTENT, NAVIGATION> : ViewModel() {
    private val initialState: UI_STATE by lazy { createInitialState() }
    private val _uiState: MutableStateFlow<UI_STATE> = MutableStateFlow(initialState)

    val uiState: StateFlow<UI_STATE> = _uiState.asStateFlow()

    private val intents: MutableSharedFlow<INTENT> = MutableSharedFlow()

    private val _navigation: MutableSharedFlow<NAVIGATION> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    init {
        subscribeIntents()
    }

    private fun subscribeIntents() {
        viewModelScope.launch {
            try {
                intents.collect { intent ->
                    handleIntent(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setIntent(intent: INTENT) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    protected fun setUiState(reducer: UI_STATE.() -> UI_STATE) {
        val newState = _uiState.value.reducer()
        _uiState.value = newState
    }

    protected fun goNavigation(navigation: NAVIGATION) {
        viewModelScope.launch {
            _navigation.emit(navigation)
        }
    }

    protected abstract fun createInitialState(): UI_STATE

    protected abstract suspend fun handleIntent(intent: INTENT)
}
