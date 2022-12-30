package dev.ranjan.androidnewlearnings

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okio.Buffer
import java.io.IOException

class EncryptionInterceptor(mEncryptionStrategy: CryptoStrategy?) :
    Interceptor {
    private val mEncryptionStrategy: CryptoStrategy?

    //injects the type of encryption to be used
    init {
        this.mEncryptionStrategy = mEncryptionStrategy
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i(TAG, "===============ENCRYPTING REQUEST===============")
        var request: Request = chain.request()
        val rawBody: RequestBody? = request.body
        var encryptedBody = ""
        val mediaType: MediaType = "text/plain; charset=utf-8".toMediaType()
        if (mEncryptionStrategy != null) {
            try {
                val buffer = Buffer()
                rawBody?.writeTo(buffer)
                val rawBodyStr: String = buffer.readUtf8()
                encryptedBody = mEncryptionStrategy.encrypt(rawBodyStr)
                Log.i(TAG, "Raw body=>  $rawBodyStr.toString()")
                Log.i(TAG, "Encrypted BODY=> $encryptedBody")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            throw IllegalArgumentException("No encryption strategy!")
        }
        val body: RequestBody = create(mediaType, encryptedBody)

        //build new request
        request = request.newBuilder()
            .header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method(), body).build()
        return chain.proceed(request)
    }

    companion object {
        private val TAG = EncryptionInterceptor::class.java.simpleName
    }
}