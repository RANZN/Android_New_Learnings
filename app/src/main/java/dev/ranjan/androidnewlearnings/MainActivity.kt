package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
        OneTimeWorkRequest - Runs the task only once
        PeriodicWorkTimeRequest - Runs the task after a certain time interval.
         */
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ChargingWorkerClass>()
            //Minimum time interval to run a periodic task is 15 minutes
            .setInitialDelay(10, TimeUnit.SECONDS).setConstraints(
                Constraints.Builder().setRequiresCharging(true).build()
            ).build()

        val myConstraints = Constraints.Builder()
            .setRequiresCharging(true) //checks whether device should be charging for the WorkRequest to run
            .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
            .setRequiresBatteryNotLow(true) // checks whether device battery should have a specific level to run the work request
            .setRequiresStorageNotLow(true) // checks whether device storage should have a specific level to run the work request
            .build()


        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<ChargingWorkerClass>(1, TimeUnit.HOURS).setConstraints(
                myConstraints
            ).build()


        val workerManager = WorkManager.getInstance(applicationContext)

        //this is how you can schedule your task using Work Manager.
        workerManager.enqueue(oneTimeWorkRequest)


//        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(chargingWorker.id)

        workerManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "onCreate: Task Completed")
            }
        }
        /**
         * Chaining of tasks
         */

        val anotherWorkRequest = OneTimeWorkRequestBuilder<AnotherWorkerClass>().build()
        workerManager.beginWith(oneTimeWorkRequest).then(anotherWorkRequest).enqueue()

        workerManager.getWorkInfoByIdLiveData(anotherWorkRequest.id).observe(this){workInfo->
            if(workInfo!=null && workInfo.state==WorkInfo.State.SUCCEEDED)
            {
                Toast.makeText(this, "Another Task Completed", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "onCreate: Another Task Completed")
            }
        }
        /**
        Parallel Chaining
         */
        workerManager.beginWith(listOf(oneTimeWorkRequest, anotherWorkRequest))
            .then(anotherWorkRequest).enqueue()

        /**
         * Canceling of Work
         */
//        workerManager.cancelWorkById(anotherWorkRequest.id) //canceling single work

//        workerManager.cancelAllWork() //canceling all works

        /**
        Assigning work request some default parameter
         */
        val newWorkRequest = OneTimeWorkRequestBuilder<ChargingWorkerClass>()
            .setInputData(workDataOf(Pair("userID", 1))).build()

        workerManager.enqueue(newWorkRequest)
//        workerManager.cancelWorkById(newWorkRequest.id)
    }
}