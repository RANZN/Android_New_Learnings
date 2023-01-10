package dev.ranjan.androidnewlearnings

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ChargingWorkerClass(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.d("TAG", "doWork: work Started")
        val userId =
            inputData.getString("userID") //works only when user passes some data while assigning workRequest
        return Result.success()
    }
}

