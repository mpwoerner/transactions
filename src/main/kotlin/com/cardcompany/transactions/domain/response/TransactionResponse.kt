package com.cardcompany.transactions.domain.response

data class TransactionResponse(
    val transactionId: String,
    val date: String,
    val amount: Double,
    val merchantName: String,
    val summary: String,
    val accountId: String
)
