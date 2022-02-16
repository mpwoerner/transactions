package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.repository.AccountRepository
import com.cardcompany.transactions.repository.TransactionRepository
import com.cardcompany.transactions.utils.TestTransactions.transaction1
import com.cardcompany.transactions.utils.TestTransactions.transaction2
import com.cardcompany.transactions.utils.TestTransactions.transaction3
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TransactionsServiceTest {
    @MockK
    private lateinit var mockTransactionRepository: TransactionRepository

    @MockK
    private lateinit var mockAccountRepository: AccountRepository

    @InjectMockKs
    private lateinit var transactionsService: TransactionsService

    @Test
    fun `getTransactions() calls transaction and account repositories and returns empty list`() {
        every { mockAccountRepository.findByAccountId(any()) } returns Account(memberName = "Tony Soprano")
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns emptyList()

        val transactionResponseList = transactionsService.getTransactions(123L, "2022-02-01")

        transactionResponseList.size shouldBeEqualTo 0
        verify { mockAccountRepository.findByAccountId(123L) }
        verify { mockTransactionRepository.findAllByAccount_AccountId(123L) }
    }

    @Test
    fun `getTransactions() returns list of transactionResponse`() {
        every { mockAccountRepository.findByAccountId(any()) } returns Account(memberName = "Tony Soprano")
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns listOf(transaction1)

        val transactionResponseList = transactionsService.getTransactions(123L, null)

        transactionResponseList.size shouldBeEqualTo 1
        transactionResponseList[0].accountId shouldBeEqualTo "456"
        transactionResponseList[0].date shouldBeEqualTo "2022-02-01"
    }

    @Test
    fun `getTransactions() throws accountNotFoundException when account does not exist, does not call transactionRepository`() {
        every { mockAccountRepository.findByAccountId(any()) } returns null

        invoking { transactionsService.getTransactions(123L, null) } shouldThrow AccountNotFoundException::class

        verify { mockAccountRepository.findByAccountId(123L) }
        verify(exactly = 0) { mockTransactionRepository.findAllByAccount_AccountId(any()) }
    }

    @Test
    fun `getTransactions() filters transactions by date when fromDate parameter passed`() {
        every { mockAccountRepository.findByAccountId(any()) } returns Account(memberName = "Tony Soprano")
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns listOf(
            transaction1,
            transaction2,
            transaction3
        )

        val transactionResponseList = transactionsService.getTransactions(456, "2022-02-02")

        transactionResponseList.size shouldBeEqualTo 2
        transactionResponseList[0].accountId shouldBeEqualTo "456"
        transactionResponseList[0].date shouldBeEqualTo "2022-02-02"
        transactionResponseList[1].date shouldBeEqualTo "2022-02-03"
    }
}