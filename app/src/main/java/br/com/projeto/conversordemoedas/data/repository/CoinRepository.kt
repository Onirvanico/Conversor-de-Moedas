package br.com.projeto.conversordemoedas.data.repository

import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.data.model.CoinContentResponse
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoinContentResponse(coins: String): Flow<CoinContent>

    suspend fun save(exchange: CoinContent)

    fun getList(): Flow<List<CoinContent>>
}