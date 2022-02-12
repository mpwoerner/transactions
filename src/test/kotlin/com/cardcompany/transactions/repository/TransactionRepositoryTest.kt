package com.cardcompany.transactions.repository

import com.cardcompany.transactions.domain.Account
import com.cardcompany.transactions.domain.Transaction
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
class TransactionRepositoryTest(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val accountRepository: AccountRepository
) {
    @BeforeEach
    fun setUp() {
        initializeDb()
    }

    @Test
    fun `findAllByAccount_AccountId returns list of transactions for given account`() {
        val actualResponse = transactionRepository.findAllByAccount_AccountId(123L)
        actualResponse.size `should be equal to` 1
        actualResponse[0].account?.accountId `should be equal to` 123L
    }

    private fun initializeDb() {
        saveAccountOne()
        saveAccountTwo()
    }

    private fun saveAccountOne() {
        val account1 = Account(
            accountId = 789L,
            memberName = "Bruce Wayne",
            transactions = null
        ).apply {
            this.transactions = setOf(
                aTransactionWith(this, LocalDate.of(2022, 2, 3).toEpochDay(), 3L),
                aTransactionWith(this, LocalDate.of(2022, 2, 2).toEpochDay(), 2L),
                aTransactionWith(this, LocalDate.of(2022, 2, 1).toEpochDay(), 1L)
            )
        }
        accountRepository.save(account1)
    }

    private fun saveAccountTwo() {
        val account2 = Account(
            accountId = 123L,
            memberName = "Tony Soprano",
            transactions = null
        ).apply {
            this.transactions = setOf(Transaction(
                456L,
                LocalDate.of(2022, 2, 2).toEpochDay(),
                50.00,
                "Amazon",
                "XP Explained Book",
                this
            ))
        }
        accountRepository.save(account2)
    }

    private fun aTransactionWith(account: Account, date: Long, transactionId: Long): Transaction {
        return Transaction(
            transactionId = transactionId,
            date = date,
            amount = 50.00,
            merchantName = "Amazon",
            summary = "TDD By Example",
            account = account
        )
    }
}