package dev.ranjan.androidnewlearnings.common

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T, code: Int) : Resource<T>(data = data, code = code)
    class Loading<T> : Resource<T>()
    class Error<T>(errorMessage: String, code: Int? = null) :
        Resource<T>(message = errorMessage, code = code)
}

suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>) = flow {
    emit(Resource.Loading())
    try {
        val response: Response<T> = apiToBeCalled()
        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()!!, code = response.code()))
        } else {
            emit(
                Resource.Error(
                    errorMessage = response.errorBody()?.string().toString(),
                    code = response.code()
                )
            )
        }
    } catch (e: HttpException) {
        emit(Resource.Error(e.message ?: "Something went wrong"))
    } catch (e: IOException) {
        emit(Resource.Error("Please check your network connection"))
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        emit(Resource.Error(e.message.toString()))
    }
}.catch { e -> emit(Resource.Error(e.message ?: "Error")) }
    .flowOn(Dispatchers.IO)

