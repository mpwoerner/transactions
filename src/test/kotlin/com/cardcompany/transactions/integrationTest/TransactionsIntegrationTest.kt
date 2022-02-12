package com.cardcompany.transactions.integrationTest

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.repository.AccountRepository
import com.cardcompany.transactions.utils.aTransactionWith
import com.cardcompany.transactions.utils.readFile
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TransactionsIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val accountRepository: AccountRepository
) {

    private final val ACCOUNT_WITH_NO_TRANSACTIONS = Account(
        accountId = 123L,
        memberName = "Tony Soprano"
    )

    private final val ACCOUNT_WITH_TRANSACTIONS = Account(
        accountId = 456L,
        memberName = "Paulie Walnuts",
    ).apply {
        this.transactions = setOf(
            aTransactionWith(this, LocalDate.of(2022, 2, 3).toEpochDay(), 1L)
        )
    }

    @BeforeEach
    fun setUp() {
        accountRepository.save(ACCOUNT_WITH_NO_TRANSACTIONS)
        accountRepository.save(ACCOUNT_WITH_TRANSACTIONS)
    }

    @AfterEach
    fun tearDown() {
        accountRepository.deleteAll()
    }

    /**
     * Scenario 1: Retrieve transactions for account with no transactions
     * GIVEN a card member account with no transactions
    `* WHEN I request a list of transactions for that account
     * THEN I will receive a success response
     * AND I will see an empty list of transactions
     * */
    @Test
    fun `returns empty transaction list for account with no transactions`() {
        mockMvc.get("/accounts/123/transactions")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { json("[]") }
            }
    }

    /**
     * Scenario 2: Retrieve transactions for an account with one or more transactions
     * GIVEN a card member account with transactions
     * WHEN I request a list of transactions for that account
     * THEN I will receive a success response
     * AND I will a list of all their transactions
     */
    @Test
    fun `returns transaction list for account with transactions`() {
        val expectedResponse = readFile("account-with-transactions.json")
        mockMvc.get("/accounts/456/transactions")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { json(expectedResponse, strict = false) }
            }
    }
}