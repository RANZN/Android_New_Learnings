package dev.ranjan.androidnewlearnings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ranjan.androidnewlearnings.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<TheViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.callSomeData()
    }
}