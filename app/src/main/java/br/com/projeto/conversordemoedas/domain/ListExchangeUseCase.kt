package br.com.projeto.conversordemoedas.domain

import br.com.projeto.conversordemoedas.core.UseCase
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(
    private val repository: CoinRepository
    ) : UseCase.NoParam<List<CoinContent>>() {

    override suspend fun execute(): Flow<List<CoinContent>> {
        return repository.getList()
    }
}