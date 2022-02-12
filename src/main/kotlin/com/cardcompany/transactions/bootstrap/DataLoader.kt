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

    private fun initializeDb() {
        saveAccountOne()
        saveAccountTwo()
    }

    private fun saveAccountOne() {
        val account1 = Account(
            accountId = 789L,
            memberName = "Bruce Wayne"
        )
        accountRepository.save(account1)
    }

    private fun saveAccountTwo() {
        val account2 = Account(
            accountId = 123L,
            memberName = "Tony Soprano"
        ).apply {
            this.transactions = setOf(
                Transaction(
                    transactionId = 1L,
                    date = LocalDate.of(2022, 2, 2).toEpochDay(),
                    amount = 50.00,
                    merchantName = "Amazon",
                    summary = "XP Explained Book",
                    account = this
                )
            )
        }
        accountRepository.save(account2)
    }
}