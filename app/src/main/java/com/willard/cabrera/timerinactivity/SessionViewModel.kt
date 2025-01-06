package com.willard.cabrera.timerinactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.willard.cabrera.timerinactivity.tracker.AppStateTracker
import com.willard.cabrera.timerinactivity.tracker.InactivityTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SessionViewModel(
    appStateTracker: AppStateTracker,
    private val workManager: WorkManager,
    private val logoutWorkRequest: OneTimeWorkRequest,
) : ViewModel() {

    private val uniqueWorkName: String
        get() = "logout_work"


    private val inactivityTracker: InactivityTracker by lazy {
        InactivityTracker()
    }

    private val _sessionState = MutableStateFlow(true)
    val sessionState: StateFlow<Boolean> get() = _sessionState

    init {
        viewModelScope.launch {
            combine(
                inactivityTracker.userInactivity,
                appStateTracker.appState
            ) { isInactive, isForeground ->
                isInactive || !isForeground
            }.collect { shouldLogout ->
                if (shouldLogout) {
                    performLogout()
                }
            }
        }
    }

    fun onUserInteraction() {
        inactivityTracker.userInteraction()
    }

    private fun performLogout() {
        _sessionState.value = false
        println("performLogout")
        workManager.enqueueUniqueWork(
            uniqueWorkName,
            ExistingWorkPolicy.REPLACE,
            logoutWorkRequest
        )
    }

    fun resetSession() {
        _sessionState.value = true
        println("resetSession")
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }
}
