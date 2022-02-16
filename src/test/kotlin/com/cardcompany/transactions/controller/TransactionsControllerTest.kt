package com.cardcompany.transactions.controller

import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.domain.response.TransactionResponse
import com.cardcompany.transactions.service.TransactionsService
import com.cardcompany.transactions.utils.readFile
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class)
class TransactionsControllerTest {

    @MockK
    private lateinit var mockTransactionsService: TransactionsService

    @InjectMockKs
    private lateinit var transactionsController: TransactionsController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(transactionsController)
            .setControllerAdvice(TransactionsControllerAdvice())
            .build()
    }

    @Test
    fun `returns empty transactionResponse list when service returns empty list`() {
        every { mockTransactionsService.getTransactions(any()) } returns (emptyList())

        mockMvc.get("/accounts/123/transactions").andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `returns transactionResponse list when service returns list of transactionResponse`() {
        every { mockTransactionsService.getTransactions(any()) } returns (
            listOf(
                TransactionResponse(
                    transactionId = "1",
                    date = "2022-02-03",
                    amount = 50.00,
                    merchantName = "Amazon",
                    summary = "TDD By Example",
                    accountId = "456"
                )
            )
            )
        val expectedResponse = readFile("account-with-transactions.json")

        mockMvc.get("/accounts/456/transactions").andExpect {
            status { isOk() }
            content { json(expectedResponse) }
        }
    }

    @Test
    fun `returns error message with 404 when service throws accountNotFoundException`() {
        every { mockTransactionsService.getTransactions(any()) } throws AccountNotFoundException(123)
        mockMvc.get("/accounts/123/transactions")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
                content {
                    jsonPath(
                        "$.error.message",
                        `is`("The card member account with the id of 123 was not found.")
                    )
                }
                content { jsonPath("$.error.type", `is`("AccountNotFoundException")) }
            }
    }
}