package com.willard.cabrera.timerinactivity.tracker

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class InactivityTracker(
    private val timeoutDuration: Long = 10 * 1000L
) {
    private val _userActivity = MutableStateFlow(System.currentTimeMillis())
    val userInactivity: Flow<Boolean> = _userActivity
        .map { System.currentTimeMillis() - it }
        .flatMapLatest { elapsedTime ->
            flow {
                val remainingTime = timeoutDuration - elapsedTime
                if (remainingTime > 0) {
                    delay(remainingTime)
                }
                emit(true)
            }
        }

    fun userInteraction() {
        println("userInteraction ${System.currentTimeMillis()}")
        _userActivity.value = System.currentTimeMillis()
    }
}
