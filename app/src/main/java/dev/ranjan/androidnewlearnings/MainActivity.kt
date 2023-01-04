package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.ranjan.androidnewlearnings.common.Resource
import dev.ranjan.androidnewlearnings.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TheViewModel>()
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress) }

    @Inject
    lateinit var apiService:ApiService
    private val mediaType = "application/json".toMediaTypeOrNull()
    private val body = "{\"audience\": \"https://api.bravenewcoin.com\",\"client_id\": \"oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY\",\"grant_type\": \"client_credentials\" }"
        .toRequestBody(mediaType)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        CoroutineScope(Dispatchers.IO).launch {
//            val response=apiService.getToken(body)
//            Log.d("TAG", "onCreate: ${response.body()}")
//        }
//
//        lifecycleScope.launch {
//            viewModel.getToken().observe(this@MainActivity) {
//                progressBar.visibility = View.GONE
//                when (it) {
//                    is Resource.Success -> {
//                        textView.text = it.data.toString()
//                        textView.setTextColor(resources.getColor(R.color.black))
//                    }
//                    is Resource.Error -> {
//                        textView.text = "${it.message} ${it.code}"
//                        textView.setTextColor(resources.getColor(R.color.red))
//                    }
//                    is Resource.Loading -> {
//                        progressBar.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }

        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body =
            "{\"audience\": \"https://api.bravenewcoin.com\",\"client_id\": \"oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY\",\"grant_type\": \"client_credentials\" }".toRequestBody(
                mediaType
            )
        val request = Request.Builder()
            .url("https://bravenewcoin.p.rapidapi.com/oauth/token")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "d0e014b3cbmsh6771f52d06a4ba4p1c72e7jsnf6d857f1d8df")
            .addHeader("X-RapidAPI-Host", "bravenewcoin.p.rapidapi.com")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val response = client.newCall(request).execute()
            Log.d("TAG", "onCreate: $response")
            withContext(Dispatchers.Main){
                textView.text=response.message.toString()
            }
        }
    }
}
