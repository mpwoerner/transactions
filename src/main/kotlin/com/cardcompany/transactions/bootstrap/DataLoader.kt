package com.cardcompany.transactions.bootstrap

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.entity.Transaction
import com.cardcompany.transactions.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Profile("!test")
@Component
class DataLoader(
    @Autowired private val accountRepository: AccountRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        initializeDb()
    }

    fun initializeDb() {
        accountRepository.saveAll(
            listOf(
                ACCOUNT_WITH_NO_TRANSACTIONS,
                ACCOUNT_WITH_ONE_TRANSACTION,
                ACCOUNT_WITH_MULTIPLE_TRANSACTIONS
            )
        )
    }

    private val ACCOUNT_WITH_NO_TRANSACTIONS =
        Account(
            accountId = 123L,
            memberName = "Christopher Moltisanti"
        )

    private val ACCOUNT_WITH_ONE_TRANSACTION =
        Account(
            accountId = 456L,
            memberName = "Tony Soprano"
        ).apply {
            this.transactions = setOf(
                aTransactionWith(this, LocalDate.of(2022, 2, 3).toEpochDay(), 1L, "Amazon", "TDD By Example")
            )
        }

    private val ACCOUNT_WITH_MULTIPLE_TRANSACTIONS =
        Account(
            accountId = 789L,
            memberName = "Paulie Walnuts"
        ).apply {
            this.transactions = setOf(
                aTransactionWith(this, LocalDate.of(2022, 2, 3).toEpochDay(), 4L, "Walmart", "XP Poster"),
                aTransactionWith(this, LocalDate.of(2022, 2, 2).toEpochDay(), 3L, "Etsy", "How to TDD"),
                aTransactionWith(this, LocalDate.of(2022, 2, 1).toEpochDay(), 2L, "Ikea", "TDD bøøkshelf")
            )
        }

    private fun aTransactionWith(
        account: Account,
        date: Long,
        transactionId: Long,
        merchantName: String = "Amazon",
        summary: String = "TDD By Example"
    ): Transaction {
        return Transaction(
            transactionId = transactionId,
            date = date,
            amount = 50.00,
            merchantName = merchantName,
            summary = summary,
            account = account
        )
    }
}