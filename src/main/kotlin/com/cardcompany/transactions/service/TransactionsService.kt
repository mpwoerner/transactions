package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.TransactionResponse
import com.cardcompany.transactions.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionsService(
    private val transactionRepository: TransactionRepository
) {
    fun getTransactions(accountId: Long): List<TransactionResponse>? {
        transactionRepository.findAllByAccount_AccountId(accountId)
        return emptyList()
    }
}
