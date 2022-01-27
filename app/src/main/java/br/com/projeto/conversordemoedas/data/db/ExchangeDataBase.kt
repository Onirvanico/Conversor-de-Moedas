package br.com.projeto.conversordemoedas.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.projeto.conversordemoedas.data.db.dao.ExchangeDAO
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.tasks.database.converter.DateConverter

@TypeConverters(DateConverter::class)
@Database(entities = [CoinContent::class], version = 2, exportSchema = false)
abstract class ExchangeDataBase : RoomDatabase() {

    companion object {
        fun getInstace(context: Context): ExchangeDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ExchangeDataBase::class.java,
                "exchange_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun exchangeDao(): ExchangeDAO
}