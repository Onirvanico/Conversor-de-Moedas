package br.com.projeto.conversordemoedas.data.model

data class ErrorResponse (
    val status: Long,
    val code: String,
    val message: String
)