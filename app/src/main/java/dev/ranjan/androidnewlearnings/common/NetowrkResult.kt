package dev.ranjan.androidnewlearnings.common

import android.util.Log
import dev.ranjan.androidnewlearnings.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.FileNotFoundException
import java.io.IOException

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T, code: Int) : Resource<T>(data = data, code = code)
    class Loading<T> : Resource<T>()
    class Error<T>(errorMessage: String, code: Int? = null) :
        Resource<T>(message = errorMessage, code = code)
}

suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Flow<Resource<T>> {
    return withContext(Dispatchers.IO) {
        flow {
            emit(Resource.Loading())
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()!!, code = response.code()))
                } else {
                    emit(Resource.Error(errorMessage = "Something went wrong", code = response.code()))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.message ?: "Something went wrong"))
            } catch (e: IOException) {
                Log.d("ranjan", "safeApiCall: ${e.message}")
                emit(Resource.Error(e.message ?: "Please check your network connection"))
                if (e is FileNotFoundException)
                    emit(Resource.Error("File Not Found: ${e.message} "))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}