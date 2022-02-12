package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.response.TransactionResponse
import com.cardcompany.transactions.repository.TransactionRepository
import com.cardcompany.transactions.utils.formatAsString
import org.springframework.stereotype.Service

@Service
class TransactionsService(
    private val transactionRepository: TransactionRepository
) {
    fun getTransactions(accountId: Long): List<TransactionResponse>? {
        val transactionList = transactionRepository.findAllByAccount_AccountId(accountId)
        return transactionList.map {
            TransactionResponse(
                transactionId = it.transactionId.toString(),
                date = it.date.formatAsString("yyyy-MM-dd"),
                amount = it.amount,
                merchantName = it.merchantName,
                summary = it.summary,
                accountId = it.account.accountId.toString()
            )
        }
    }
}
