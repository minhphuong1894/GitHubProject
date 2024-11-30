package com.steven.domain.usecases

import com.steven.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Resource<R>> = execute(parameters)
        .catch { e ->
            emit(Resource.Error(e))
        }
        .flowOn(dispatcher)

    protected abstract fun execute(parameters: P): Flow<Resource<R>>
}
