package br.com.projeto.conversordemoedas.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.projeto.conversordemoedas.data.db.dao.ExchangeDAO
import br.com.projeto.conversordemoedas.data.model.CoinContent

@Database(entities = [CoinContent::class], version = 1)
abstract class ExchangeDataBase : RoomDatabase() {

    companion object {
        fun getInstace(context: Context): ExchangeDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ExchangeDataBase::class.java,
                "exchange_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun exchangeDao(): ExchangeDAO
}