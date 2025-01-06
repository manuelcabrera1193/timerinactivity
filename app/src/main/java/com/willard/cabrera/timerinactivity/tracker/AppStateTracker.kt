package com.willard.cabrera.timerinactivity.tracker

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppStateTracker : DefaultLifecycleObserver {

    private val _appState = MutableStateFlow(true)
    val appState: StateFlow<Boolean>
        get() = _appState

    override fun onStart(owner: LifecycleOwner) {
        _appState.value = true
    }

    override fun onStop(owner: LifecycleOwner) {
        _appState.value = false
    }
}
