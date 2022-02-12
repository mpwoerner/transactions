package com.cardcompany.transactions.controller

import com.cardcompany.transactions.service.TransactionsService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
        mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build()
    }

    @Test
    fun `returns empty transactions when service returns empty transactions`() {
        every { mockTransactionsService.getTransactions(any()) } returns(emptyList())

        mockMvc.get("/accounts/123/transactions").andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }
}