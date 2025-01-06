package com.willard.cabrera.timerinactivity

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.willard.cabrera.timerinactivity.tracker.AppStateTracker
import com.willard.cabrera.timerinactivity.worker.LogoutWorker
import java.util.UUID

class TimerInactivityApp : Application() {

    val workManager: WorkManager by lazy {
        WorkManager.getInstance(this)
    }

    private val workerUUID: UUID by lazy {
        UUID.randomUUID()
    }

    val logoutWorkRequest: OneTimeWorkRequest by lazy {
        OneTimeWorkRequestBuilder<LogoutWorker>().setId(workerUUID).build()
    }

    val appStateTracker: AppStateTracker by lazy {
        AppStateTracker()
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appStateTracker)
        workManager.getWorkInfoByIdLiveData(workerUUID)
            .observe(ProcessLifecycleOwner.get()) { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    println("The Worker finish with state: ${workInfo.state}")
                }
            }
    }

    override fun onTerminate() {
        super.onTerminate()
        println("onTerminate")
    }
}
