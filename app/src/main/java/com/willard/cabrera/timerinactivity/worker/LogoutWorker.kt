package com.willard.cabrera.timerinactivity.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LogoutWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {

            clearLocalData()
            notifyBackendLogout()
            logLogoutCompletion()

            return Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }

    private fun clearLocalData() {
        println("clear data local")
    }

    private fun notifyBackendLogout() {
        println("Notify backend logout")
    }

    private fun logLogoutCompletion() {
        println("logout completed")
    }
}
