package com.cardcompany.transactions.utils

import com.cardcompany.transactions.domain.response.TransactionResponse

object TestTransactionResponses {
    val transactionResponseList = listOf(
        TransactionResponse(
            transactionId = "1",
            date = "2022-02-03",
            amount = 50.00,
            merchantName = "Amazon",
            summary = "TDD By Example",
            accountId = "456"
        )
    )
    val filteredTransactionResponseList = listOf(
        TransactionResponse(
            transactionId = "3",
            date = "2022-02-02",
            amount = 50.00,
            merchantName = "Etsy",
            summary = "How to TDD",
            accountId = "789"
        ),
        TransactionResponse(
            transactionId = "4",
            date = "2022-02-03",
            amount = 50.00,
            merchantName = "Walmart",
            summary = "XP Poster",
            accountId = "789"
        )
    )
}