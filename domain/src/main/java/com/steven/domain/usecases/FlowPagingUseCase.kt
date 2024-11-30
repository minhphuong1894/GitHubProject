package com.steven.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowPagingUseCase<in P, R : Any>(private val dispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<PagingData<R>> = execute(parameters)
        .flowOn(dispatcher)

    protected abstract fun execute(parameters: P): Flow<PagingData<R>>
}