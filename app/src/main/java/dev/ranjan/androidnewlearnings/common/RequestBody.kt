package dev.ranjan.androidnewlearnings.common

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

interface ProgressCallback {
    fun onProgress(progress: Long)
    fun onSuccess(file: String)
    fun onError(error: String, responseCode: Int?)
}

internal class ProgressRequestBody(
    private val file: File, private val callback: ProgressCallback
) : RequestBody() {

    override fun contentType(): MediaType = "*/*".toMediaType()

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val total = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var uploaded = 0L
        FileInputStream(file).use { fis ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (fis.read(buffer).also { read = it } != -1) {
                handler.post {
                    val value = 100 * uploaded / total
                    callback.onProgress(value)
                }
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }

}

suspend fun <T> uploadData(
    callback: ProgressCallback, apiToBeCalled: suspend () -> retrofit2.Response<T>,
) {
    return withContext(Dispatchers.IO) {
        try {
            val response: retrofit2.Response<T> = apiToBeCalled()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    callback.onSuccess(response.body().toString())
                } else {
                    callback.onError("Something went wrong", response.code())
                }
            }
        } catch (e: Exception) {
            callback.onError(e.message.toString(), null)
        }
    }
}