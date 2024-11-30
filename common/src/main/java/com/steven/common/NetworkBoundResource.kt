package com.steven.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<RequestType, ResultType> {

    fun execute(): Flow<Resource<ResultType>> {
        return flow {
            try {
                val data = loadFromDb()
                preFetch(data)
                if (shouldFetch(data)) {
                    val result = createCall()
                    if (shouldClearCurrentData()) {
                        clearCurrentData()
                    }
                    saveCallResult(result)
                    emit(Resource.Success(loadFromDb()!!))
                } else {
                    if (data != null) {
                        emit(Resource.Success(data))
                    } else {
                        emit(Resource.Error(Exception("There is no data")))
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Resource.Error(exception))
            }
        }
    }

    protected open suspend fun preFetch(data: ResultType?) = Unit

    protected open suspend fun shouldFetch(data: ResultType?): Boolean = true

    protected open suspend fun shouldClearCurrentData() = false

    protected open suspend fun clearCurrentData() = Unit

    protected abstract suspend fun loadFromDb(): ResultType?

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(result: RequestType)
}
