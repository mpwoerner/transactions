package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.entity.Transaction
import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.repository.AccountRepository
import com.cardcompany.transactions.repository.TransactionRepository
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
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class TransactionsServiceTest {
    @MockK
    private lateinit var mockTransactionRepository: TransactionRepository

    @MockK
    private lateinit var mockAccountRepository: AccountRepository

    @InjectMockKs
    private lateinit var transactionsService: TransactionsService

    @Test
    fun `getTransactions() calls transaction and account repositories`() {
        every { mockAccountRepository.findByAccountId(any()) } returns Account(memberName = "Tony Soprano")
        every { mockTransactionRepository.findAllByAccount_AccountId(any()) } returns emptyList()

        transactionsService.getTransactions(123L)

        verify { mockAccountRepository.findByAccountId(123L) }
        verify { mockTransactionRepository.findAllByAccount_AccountId(123L) }
    }

    @Test
    fun `getTransactions() returns list of transactionResponse`() {
        every { mockAccountRepository.findByAccountId(any()) } returns Account(memberName = "Tony Soprano")
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

        transactionResponseList.size shouldBeEqualTo 1
        transactionResponseList[0].accountId shouldBeEqualTo "456"
        transactionResponseList[0].date shouldBeEqualTo "2022-02-01"
    }

    @Test
    fun `getTransactions throws accountNotFoundException when account does not exist, does not call transactionRepository`() {
        every { mockAccountRepository.findByAccountId(any()) } returns null

        invoking { transactionsService.getTransactions(123L) } shouldThrow AccountNotFoundException::class

        verify { mockAccountRepository.findByAccountId(123L) }
        verify(exactly = 0) { mockTransactionRepository.findAllByAccount_AccountId(any()) }
    }
}