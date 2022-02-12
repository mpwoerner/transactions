package com.cardcompany.transactions.integrationTest

import com.cardcompany.transactions.domain.Account
import com.cardcompany.transactions.repository.AccountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val accountRepository: AccountRepository
) {

    private final val ACCOUNT_WITH_NO_TRANSACTIONS = Account(
        accountId = 123L,
        memberName = "Tony Soprano",
        emptySet()
    )

    @BeforeEach
    fun setUp() {
        accountRepository.save(ACCOUNT_WITH_NO_TRANSACTIONS)
    }

    @Test
    fun `returns empty transaction list for account with no transactions`() {
        mockMvc.get("/accounts/123/transactions").andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }
}