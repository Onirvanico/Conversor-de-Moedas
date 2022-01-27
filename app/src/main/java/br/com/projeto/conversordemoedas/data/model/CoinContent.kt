package br.com.projeto.conversordemoedas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

typealias CoinContentResponse = HashMap<String, CoinContent>

@Entity(tableName = "CoinContent")
data class CoinContent (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: String,
    val codein: String,
    val name: String,
    val bid: Double,
    var time: Date
)
