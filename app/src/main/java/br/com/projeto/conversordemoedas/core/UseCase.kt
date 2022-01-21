package br.com.projeto.conversordemoedas.core

import kotlinx.coroutines.flow.Flow
import java.lang.UnsupportedOperationException

abstract class UseCase<Param, Source> {
    abstract suspend fun execute(param: Param): Flow<Source>

    open suspend operator fun invoke (param: Param) = execute(param)

    abstract class NoParam<Source> : UseCase<Nothing, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>

        final override suspend fun execute(param: Nothing) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): Flow<Source> = execute()
    }

    abstract class NoSource<Params> : UseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    object Nothing
}