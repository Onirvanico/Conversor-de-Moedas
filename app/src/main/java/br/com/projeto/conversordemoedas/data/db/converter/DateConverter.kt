package br.com.projeto.tasks.database.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toLong(date: Date) = date.time

    @TypeConverter
    fun toDate(value: Long) = Date(value)

}