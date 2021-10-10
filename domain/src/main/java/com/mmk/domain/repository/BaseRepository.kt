package com.mmk.domain.repository

import com.mmk.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun <T> executeInBackground(
        func: suspend () -> Result<T>
    ): Result<T> = withContext(dispatcher) {
        try {
            func.invoke()
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}
