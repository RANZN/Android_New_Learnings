package dev.ranjan.androidnewlearnings

import android.util.Base64
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


/**
 * A utitliy class for encryption and decryption logic
 *
 *
 * You can implement any kind of encryption and decryption logic per your security requirements
 *
 *
 * The Encryption/Decryption is not to be used for a production app.
 * This is just a demo, proper security guidelines should be followed
 */
object CryptoUtil {
    private const val pswdIterations = 10
    private const val keySize = 128
    private const val cypherInstance = "AES/CBC/PKCS5Padding"
    private const val secretKeyInstance = "PBKDF2WithHmacSHA1"
    private const val plainText = "sampleText"
    private const val AESSalt = "exampleSalt"
    private const val initializationVector = "8119745113154120"

    @Throws(Exception::class)
    fun encrypt(data: String): String {
        val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
        val cipher = Cipher.getInstance(cypherInstance)
        cipher.init(
            Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(
                initializationVector.toByteArray()
            )
        )
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(data: String?): String {
        val encryted_bytes = Base64.decode(data, Base64.DEFAULT)
        val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
        val cipher = Cipher.getInstance(cypherInstance)
        cipher.init(
            Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(
                initializationVector.toByteArray()
            )
        )
        val decrypted = cipher.doFinal(encryted_bytes)
        return String(decrypted, "UTF-8".toCharArray().toSet())
    }

    private fun getRaw(plainText: String, salt: String): ByteArray {
        try {
            val factory = SecretKeyFactory.getInstance(secretKeyInstance)
            val spec: KeySpec =
                PBEKeySpec(plainText.toCharArray(), salt.toByteArray(), pswdIterations, keySize)
            return factory.generateSecret(spec).encoded
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }

    @Throws(IOException::class)
    fun requestBodyToString(requestBody: RequestBody): String {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }
}