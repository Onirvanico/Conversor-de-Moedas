package br.com.projeto.conversordemoedas.data

import java.util.*

enum class CoinType(val locale: Locale) {
    BRL(Locale("pt", "BR")),
    USD(Locale.US),
    EUR(Locale.UK),
    JYP(Locale.JAPAN),
    CAD(Locale.CANADA),
    ARS(Locale("es", "AR"));

    companion object {
        fun getByName(name: String) = values().find{ it.name == name} ?: BRL
    }
}