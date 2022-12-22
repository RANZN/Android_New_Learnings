package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.ranjan.androidnewlearnings.data.local.RoomDatabaseClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TheViewModel>()

    @Inject
    lateinit var database: RoomDatabaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(Dispatchers.IO).launch {
//            dao.insertData(roomEntity)
        }
//
//        dao.getData().observe(this) {
//            Log.d("ranjan", "onCreate: $it")
//        }

//        viewModel.getFirstCount().observe(this){
//            Log.d("ranjan", "firstCount: $it")
//        }
//
//        viewModel.getSecondCount().observe(this){
//            Log.d("ranjan", "secondCount: $it")
//        }
//
//        viewModel.getMergedData().observe(this){
//            Log.d("ranjan", "merged: $it")
//        }

        viewModel.getLiveData().observe(this){
            Log.d("ranjan", "onCreate: $it")
        }

        viewModel.doAPiCall()

        Log.d("TAG", "onCreate: ${this.getString(Data.MALE.gender)}")
    }
}

enum class Data(val id: Int,@StringRes val gender: Int) {
    MALE(id = 1, R.string.male),
    FEMALE(2, R.string.female),
    TRANSGENDER(3, R.string.trans),
    OTHER(id = 4, gender = R.string.other),
}

