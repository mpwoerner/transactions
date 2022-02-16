package com.cardcompany.transactions.controller

import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.service.TransactionsService
import com.cardcompany.transactions.utils.TestTransactionResponses.filteredTransactionResponseList
import com.cardcompany.transactions.utils.TestTransactionResponses.transactionResponseList
import com.cardcompany.transactions.utils.readFile
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
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
        every { mockTransactionsService.getTransactions(any(), any()) } returns (emptyList())

        mockMvc.get("/accounts/123/transactions").andExpect {
            status { isOk() }
            content { json("[]") }
        }

        verify { mockTransactionsService.getTransactions(123, null) }
    }

    @Test
    fun `returns transactionResponse list when service returns list of transactionResponse`() {
        every { mockTransactionsService.getTransactions(any(), any()) } returns transactionResponseList
        val expectedResponse = readFile("account-with-transactions.json")

        mockMvc.get("/accounts/456/transactions").andExpect {
            status { isOk() }
            content { json(expectedResponse) }
        }

        verify { mockTransactionsService.getTransactions(456, null) }
    }

    @Test
    fun `returns 404 error with error response when service throws accountNotFoundException`() {
        every { mockTransactionsService.getTransactions(any(), any()) } throws AccountNotFoundException(123)

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

        verify { mockTransactionsService.getTransactions(123, null) }
    }

    @Test
    fun `calls service with fromDate query param value`() {
        every { mockTransactionsService.getTransactions(any(), any()) } returns filteredTransactionResponseList
        val expectedResponse = readFile("account-with-filtered-transactions.json")

        mockMvc.get("/accounts/789/transactions?fromDate=2022-02-02").andExpect {
            status { isOk() }
            content { json(expectedResponse) }
        }

        verify { mockTransactionsService.getTransactions(789, "2022-02-02") }
    }
}