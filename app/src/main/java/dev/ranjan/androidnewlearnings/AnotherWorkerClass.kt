package dev.ranjan.androidnewlearnings

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class AnotherWorkerClass(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        Log.d("TAG", "doWork: Another work Started")
        delay(5000)
        return Result.success()

    }

}