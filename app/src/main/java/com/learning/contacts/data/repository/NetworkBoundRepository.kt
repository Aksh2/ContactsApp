package com.learning.contacts.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.learning.contacts.network.ResponseState
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<Result, Request> {
    fun asFlow() = flow<ResponseState<Result>> {
        emit(ResponseState.loading())
        try {
            emit(ResponseState.success(fetchFromLocal().first()))

            val apiResponse = fetchFromRemote()

            val remotePosts = apiResponse.body()

            if (apiResponse.isSuccessful && remotePosts != null) {
                saveRemoteData(remotePosts)
            } else {
                emit(ResponseState.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(ResponseState.error("Network error! can't get latest posts."))
            e.printStackTrace()
        }

        emitAll(fetchFromLocal().map {
            ResponseState.success<Result>(it)
        })
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: Request)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<Result>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<Request>
}