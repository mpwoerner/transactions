package com.cardcompany.transactions.integrationTest

import com.cardcompany.transactions.repository.AccountRepository
import com.cardcompany.transactions.utils.TestAccounts.initializeDb
import com.cardcompany.transactions.utils.TestAccounts.teardownDb
import com.cardcompany.transactions.utils.readFile
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TransactionsIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
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

    /**
     * Scenario 3: Account does not exist in database
     * GIVEN a card member account id that does not exist
     * WHEN I request a list of transactions for that account
     * THEN I will receive a not found response
     * AND I will see a detailed not found error message
     */
    @Test
    fun `returns not found error response when account does not exist`() {
        mockMvc.get("/accounts/555/transactions")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
                content {
                    jsonPath(
                        "$.error.message",
                        `is`("The card member account with the id of 555 was not found.")
                    )
                }
                content { jsonPath("$.error.type", `is`("AccountNotFoundException")) }
            }
    }

    /**
     * Scenario 4: Retrieve transactions for an account after a given a date
     * GIVEN a card member account with transactions
     * WHEN I request a list of transactions after the given date
     * THEN I will receive a success response
     * AND I will see a list of transactions that occurred on and after the given date
     */
    @Test
    fun `returns filtered transactions when fromDate query paramter included in request`() {
        val expectedResponse = readFile("account-with-filtered-transactions.json")
        mockMvc.get("/accounts/789/transactions?fromDate=2022-02-02")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { json(expectedResponse, strict = false) }
            }
    }
}