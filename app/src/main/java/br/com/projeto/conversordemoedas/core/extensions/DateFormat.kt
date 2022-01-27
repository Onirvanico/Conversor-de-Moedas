package br.com.projeto.conversordemoedas.core.extensions

import java.text.SimpleDateFormat
import java.util.*

const val PATTERN_MONTH_TIMR = "dd/MM/yyyy-HH:mm"

fun Date.format(): String {
    val date = SimpleDateFormat(PATTERN_MONTH_TIMR, Locale("pt", "br")).format(this)
    return date
}