package com.cardcompany.transactions.repository

import com.cardcompany.transactions.domain.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : CrudRepository<Account, Long>