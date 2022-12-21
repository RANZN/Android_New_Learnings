package dev.ranjan.androidnewlearnings.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.ranjan.androidnewlearnings.R
import dev.ranjan.androidnewlearnings.model.local.DatabaseTable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<TheViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.callSomeData().observe(this) {
            Log.d("TAG", "onCreate: $it")

            lifecycleScope.launch {
                if (it.isNullOrEmpty().not()) {
                    it[0].key?.let { key -> viewModel.deleteData(key) }
                }
            }
        }
        viewModel.doApiCall().observe(this){
            Log.d("TAG", "onCreate: $it")
        }
    }
}