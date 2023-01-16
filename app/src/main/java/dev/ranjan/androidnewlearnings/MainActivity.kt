package dev.ranjan.androidnewlearnings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.ranjan.androidnewlearnings.common.Resource
import dev.ranjan.androidnewlearnings.common.UriUtils
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "RANJAN"

    private val viewModel by viewModels<TheViewModel>()
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress) }
    private val fileBtn by lazy { findViewById<Button>(R.id.pick_file_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileBtn.setOnClickListener {
            val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
            chooseFile.type = "*/*"
            result.launch(chooseFile)
        }

        viewModel.response.observe(this) {
            progressBar.visibility = View.GONE
            when (it) {
                is Resource.Error -> {
                    textView.setTextColor(resources.getColor(R.color.red))
                    textView.text = "${it.message} ${it.code}"
                }
                is Resource.Loading -> progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    textView.setTextColor(resources.getColor(R.color.black))
                    textView.text = it.data.toString()
                }
            }
        }
    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val fileUri = result.data?.data
                /*val parcelFileDescriptor = contentResolver.openFileDescriptor(fileUri!!, "r", null)
                val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
                val file = File(cacheDir, getFileName(fileUri))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)*/
                val filePath = UriUtils.getPathFromUri(this, fileUri!!)
                val file = File(filePath.toString())
                viewModel.sendData(file)
            }
        }


    private fun getFileName(uri: Uri): String {
        var name: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            name = cursor.getString(nameIndex)
            cursor.close()
        }
        return name ?: "file"
    }
}
