package com.cardcompany.transactions.domain.exception

class AccountNotFoundException(accountId: Long? = null)
    : RuntimeException("The card member account with the id of $accountId was not found.")