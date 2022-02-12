package com.cardcompany.transactions.repository

import com.cardcompany.transactions.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    fun findAllByAccount_AccountId(accountId: Long): List<Transaction>
}