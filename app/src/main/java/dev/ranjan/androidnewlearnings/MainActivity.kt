package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", "onCreate: ${this.getString(Data.MALE.gender)}")
    }
}

enum class Data(val id: Int,@StringRes val gender: Int) {
    MALE(id = 1, R.string.male),
    FEMALE(2, R.string.female),
    TRANSGENDER(3, R.string.trans),
    OTHER(id = 4, gender = R.string.other),
}

