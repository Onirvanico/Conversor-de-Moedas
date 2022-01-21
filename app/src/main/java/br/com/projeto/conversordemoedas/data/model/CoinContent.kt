package br.com.projeto.conversordemoedas.data.model

import androidx.room.Entity

typealias CoinContentResponse = HashMap<String, CoinContent>

@Entity(tableName = "CoinContent")
data class CoinContent (
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: Double,
    val ask: String,
    val timestamp: String,
    val createDate: String
)
