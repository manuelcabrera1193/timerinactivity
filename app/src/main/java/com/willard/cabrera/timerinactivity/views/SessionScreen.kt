package com.willard.cabrera.timerinactivity.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.willard.cabrera.timerinactivity.SessionViewModel

@Composable
fun SessionScreen(viewModel: SessionViewModel) {

    val sessionState by viewModel.sessionState.collectAsState()

    if (!sessionState) {
        LogoutScreen(viewModel)
    } else {
        ActiveSessionScreen()
    }
}

