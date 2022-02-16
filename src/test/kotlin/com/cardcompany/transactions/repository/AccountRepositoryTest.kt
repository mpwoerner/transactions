package com.cardcompany.transactions.repository

import com.cardcompany.transactions.utils.TestAccounts.initializeDb
import com.cardcompany.transactions.utils.TestAccounts.teardownDb
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class AccountRepositoryTest(
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
    fun `findByAccountId returns account for given id`() {
        val account = accountRepository.findByAccountId(789L)

        account shouldNotBe null
        account?.transactions?.size shouldBeEqualTo 3
    }

    @Test
    fun `findByAccountId returns null when account with given id does not exist`() {
        accountRepository.findByAccountId(555L) shouldBe null
    }
}