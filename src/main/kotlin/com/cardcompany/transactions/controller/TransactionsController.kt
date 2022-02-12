package com.cardcompany.transactions.controller

import com.cardcompany.transactions.domain.response.TransactionResponse
import com.cardcompany.transactions.service.TransactionsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionsController(
    private val transactionsService: TransactionsService
) {
    @GetMapping("/accounts/{accountId}/transactions")
    fun getTransactions(@PathVariable accountId: Long): ResponseEntity<List<TransactionResponse>> {
        val transactions = transactionsService.getTransactions(accountId)
        return ResponseEntity.ok(transactions)
    }

}