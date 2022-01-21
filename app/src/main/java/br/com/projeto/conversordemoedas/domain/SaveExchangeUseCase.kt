package br.com.projeto.conversordemoedas.domain

import br.com.projeto.conversordemoedas.core.UseCase
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeUseCase(
    private val repository: CoinRepository
    ) : UseCase.NoSource<CoinContent>() {

    override suspend fun execute(param: CoinContent): Flow<Unit> {
        return flow {
            repository.save(param)
        }
    }
}