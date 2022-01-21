package br.com.projeto.conversordemoedas.domain

import br.com.projeto.conversordemoedas.core.UseCase
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.data.repository.CoinRepository

class GetCoinContentUseCase(
    private val repository: CoinRepository

) : UseCase<String, CoinContent>() {
    override suspend fun execute(param: String) = repository.getCoinContentResponse(param)
}