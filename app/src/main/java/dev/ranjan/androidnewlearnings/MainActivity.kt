package dev.ranjan.androidnewlearnings

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.ranjan.androidnewlearnings.common.ProgressCallback
import dev.ranjan.androidnewlearnings.common.UriUtils
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProgressCallback {
    val TAG = "RANJAN"

    private val viewModel by viewModels<TheViewModel>()
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    private val fileBtn by lazy { findViewById<Button>(R.id.pick_file_btn) }
    private val progressBarText by lazy { findViewById<TextView>(R.id.progressText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    val getpermission = Intent()
                    getpermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    startActivity(getpermission)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    ), 101
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ), 101
                )
            }

            val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
            chooseFile.type = "*/*"
            result.launch(chooseFile)
        }
    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val fileUri = result.data?.data
                /*val parcelFileDescriptor = contentResolver.openFilveDescriptor(fileUri!!, "r", null)
               val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
               val file = File(cacheDir, getFileName(fileUri))
               val outputStream = FileOutputStream(file)
               inputStream.copyTo(outputStream)*/
                val filePath = UriUtils.getPathFromUri(this, fileUri!!)
                Log.d(TAG, "file: $filePath")
                val file = File(filePath.toString())
                viewModel.sendData(file, this)
            }
        }

    override fun onProgress(progress: Long, total: Long) {
        progressBar.visibility = View.VISIBLE
        progressBar.progress = progress.toInt()
        progressBarText.text = "$progress / $total bytes"
    }

    override fun onSuccess(file: String) {
        progressBar.visibility = View.GONE
        progressBarText.visibility = View.GONE
        textView.setTextColor(resources.getColor(R.color.black))
        textView.text = file
        progressBarText.text = "Uploaded"
    }

    override fun onError(error: String, responseCode: Int?) {
        textView.setTextColor(resources.getColor(R.color.red))
        textView.text = "$error $responseCode"
    }

}
