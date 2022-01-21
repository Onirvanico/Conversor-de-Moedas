package br.com.projeto.conversordemoedas.data.repository


import br.com.projeto.conversordemoedas.core.exceptions.RemoteException
import br.com.projeto.conversordemoedas.data.db.ExchangeDataBase
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.data.model.ErrorResponse
import br.com.projeto.conversordemoedas.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImp(
    private val exchangeDB: ExchangeDataBase,
    private val coinService: AwesomeService

) : CoinRepository {

    private val exchangeDAO = exchangeDB.exchangeDao()

     override suspend fun getCoinContentResponse(coins: String) = flow {
         try {
             val exchangeCoin = coinService.exchangeCoin(coins)
             val exchange = exchangeCoin.values.first()
             emit(exchange)

         } catch (ex: HttpException) {
            val errorJson = ex.response()?.errorBody().toString()
             val fromJson = Gson().fromJson(errorJson, ErrorResponse::class.java)
             throw RemoteException(fromJson.message)
         }
    }

    override suspend fun save(exchange: CoinContent) {
        exchangeDAO.insertExchange(exchange)
    }

    override fun getList(): Flow<List<CoinContent>> {
        return exchangeDAO.getAll()
    }
}