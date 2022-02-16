package com.cardcompany.transactions.utils

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.entity.Transaction
import java.time.LocalDate

object TestTransactions {
    val transaction1 =
        Transaction(
            transactionId = 1,
            date = LocalDate.of(2022, 2, 1).toEpochDay(),
            amount = 50.00,
            merchantName = "Amazon",
            summary = "XP Explained",
            account = Account(
                accountId = 456,
                memberName = "Tony Soprano"
            )
        )
    val transaction2 = Transaction(
        transactionId = 2,
        date = LocalDate.of(2022, 2, 2).toEpochDay(),
        amount = 75.00,
        merchantName = "Walmart",
        summary = "TDD is Awesome: A Guide",
        account = Account(
            accountId = 456,
            memberName = "Tony Soprano"
        )
    )
    val transaction3 = Transaction(
        transactionId = 3,
        date = LocalDate.of(2022, 2, 3).toEpochDay(),
        amount = 100.00,
        merchantName = "Walmart",
        summary = "Kotlin > Java: An Explainer",
        account = Account(
            accountId = 456,
            memberName = "Tony Soprano"
        )
    )
}