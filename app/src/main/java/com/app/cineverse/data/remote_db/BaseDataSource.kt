package com.app.cineverse.data.remote_db

import com.app.cineverse.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseDataSource {
    protected suspend fun <T>
            getResult(call : suspend () -> T) : Resource<T> {
        return try {
             withContext(Dispatchers.IO) {
                 val result  = call()
                 withContext(Dispatchers.Main) {
                     Resource.success(result)
                 }
            }
        } catch (e : Exception) {
            Resource.error("Network call has failed for the following reason: "
                    + (e.localizedMessage ?: e.toString()))
        }
    }

}