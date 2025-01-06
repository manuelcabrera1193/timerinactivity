package com.willard.cabrera.timerinactivity

import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.willard.cabrera.timerinactivity.tracker.AppStateTracker
import com.willard.cabrera.timerinactivity.ui.theme.TimerInactivityTheme
import com.willard.cabrera.timerinactivity.util.viewModelFactory
import com.willard.cabrera.timerinactivity.views.SessionScreen

class SessionActivity : ComponentActivity() {

    private lateinit var app: TimerInactivityApp
    private lateinit var appStateTracker: AppStateTracker
    private lateinit var workManager: WorkManager
    private lateinit var logoutWorkRequest: OneTimeWorkRequest

    private val factory: ViewModelProvider.Factory by lazy {
        viewModelFactory {
            SessionViewModel(
                appStateTracker = appStateTracker,
                workManager = workManager,
                logoutWorkRequest = logoutWorkRequest,
            )
        }
    }

    private val viewModel: SessionViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as TimerInactivityApp
        workManager = app.workManager
        logoutWorkRequest = app.logoutWorkRequest
        appStateTracker = app.appStateTracker

        val originalCallback = window.callback

        window.callback = object : Window.Callback by window.callback {
            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                println("dispatchTouchEvent $event")
                viewModel.onUserInteraction()
                return originalCallback.dispatchTouchEvent(event)
            }
        }

        setContent {
            TimerInactivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        SessionScreen(viewModel)
                    }
                }
            }
        }
    }
}

