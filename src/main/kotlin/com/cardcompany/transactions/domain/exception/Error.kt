package com.cardcompany.transactions.domain.exception

data class Error(
    val type: String,
    val message: String
)