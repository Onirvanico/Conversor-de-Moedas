package br.com.projeto.conversordemoedas.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.projeto.conversordemoedas.data.model.CoinContent
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDAO {
    @Query("SELECT * FROM CoinContent")
    fun getAll(): Flow<List<CoinContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchange(content: CoinContent)
}