package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.entity.Transaction
import com.cardcompany.transactions.repository.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class TransactionsServiceTest {
    @MockK
    private lateinit var mockTransactionRepository: TransactionRepository

    @InjectMockKs
    private lateinit var transactionsService: TransactionsService

    @Test
    fun `getTransactions() calls transactionRepository`() {
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns emptyList()

        transactionsService.getTransactions(123L)

        verify { mockTransactionRepository.findAllByAccount_AccountId(123L) }
    }

    @Test
    fun `getTransactions() returns list of transactionResponse`() {
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns listOf(
            Transaction(
                transactionId = 1,
                date = LocalDate.of(2022, 2, 1).toEpochDay(),
                amount = 50.00,
                merchantName = "Amazon",
                summary = "XP Explained",
                account = Account(
                    accountId = 456,
                    memberName = "Paulie Walnuts"
                )
            )
        )

        val transactionResponseList = transactionsService.getTransactions(123L)

        transactionResponseList?.size `should be equal to` 1
        transactionResponseList?.get(0)?.accountId `should be equal to` "456"
        transactionResponseList?.get(0)?.date `should be equal to` "2022-02-01"
    }
}