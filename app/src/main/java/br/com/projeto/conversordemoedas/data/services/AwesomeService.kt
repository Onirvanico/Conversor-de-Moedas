package br.com.projeto.conversordemoedas.data.services

import br.com.projeto.conversordemoedas.data.model.CoinContentResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {

    @GET("json/last/{coins}")
    suspend fun exchangeCoin(@Path("coins") coins: String): CoinContentResponse
}