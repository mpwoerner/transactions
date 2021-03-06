package com.cardcompany.transactions.repository

import com.cardcompany.transactions.utils.TestAccounts.initializeDb
import com.cardcompany.transactions.utils.TestAccounts.teardownDb
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class TransactionRepositoryTest(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val accountRepository: AccountRepository
) {
    @BeforeEach
    fun setUp() {
        initializeDb(accountRepository)
    }

    @AfterEach
    fun tearDown() {
        teardownDb(accountRepository)
    }

    @Test
    fun `findAllByAccount_AccountId returns list of transactions for given account`() {
        val actualResponse = transactionRepository.findAllByAccount_AccountId(456L)
        actualResponse.size shouldBeEqualTo 1
        actualResponse[0].account.accountId shouldBeEqualTo 456L
    }
}