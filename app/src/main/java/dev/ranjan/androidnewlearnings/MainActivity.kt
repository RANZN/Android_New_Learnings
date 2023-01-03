package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.ranjan.androidnewlearnings.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TheViewModel>()
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        viewModel.successResponse.observe(this) {
            textView.text = it.toString()
            textView.setTextColor(resources.getColor(R.color.black))
            Log.d("TAG", "Success: ${it.toString()}")
        }
        viewModel.errorResponse.observe(this) {
            textView.text = it.toString()
            textView.setTextColor(resources.getColor(R.color.red))
            Log.d("TAG", "Failure: ${it.toString()}")
        }
        viewModel.loading.observe(this) {
            if (it) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }*/

        lifecycleScope.launch {
            viewModel.doApiCall().observe(this@MainActivity) {
                progressBar.visibility = View.GONE
                when (it) {
                    is Resource.Success -> {
                        textView.text = it.data.toString()
                        textView.setTextColor(resources.getColor(R.color.black))
                    }
                    is Resource.Error -> {
                        textView.text = "${it.message} ${it.code}"
                        textView.setTextColor(resources.getColor(R.color.red))
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
