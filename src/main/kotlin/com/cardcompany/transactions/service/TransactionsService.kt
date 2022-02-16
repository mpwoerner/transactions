package com.cardcompany.transactions.service

import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.domain.response.TransactionResponse
import com.cardcompany.transactions.repository.AccountRepository
import com.cardcompany.transactions.repository.TransactionRepository
import com.cardcompany.transactions.utils.formatAsString
import org.springframework.stereotype.Service

@Service
class TransactionsService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    @Throws(AccountNotFoundException::class)
    fun getTransactions(accountId: Long): List<TransactionResponse> {
        return accountRepository.findByAccountId(accountId)?.let {
            val transactionList = transactionRepository.findAllByAccount_AccountId(accountId)
            transactionList.map {
                TransactionResponse(
                    transactionId = it.transactionId.toString(),
                    date = it.date.formatAsString("yyyy-MM-dd"),
                    amount = it.amount,
                    merchantName = it.merchantName,
                    summary = it.summary,
                    accountId = it.account.accountId.toString()
                )
            }
        } ?: throw AccountNotFoundException(accountId)
    }
}
