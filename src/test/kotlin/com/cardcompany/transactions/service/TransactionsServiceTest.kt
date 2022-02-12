package com.cardcompany.transactions.service

import com.cardcompany.transactions.repository.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
}