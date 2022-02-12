package com.cardcompany.transactions.domain.entity

import javax.persistence.*

@Entity
class Account(
    @Id
    @Column(name = "ACCOUNT_ID")
    val accountId: Long = 0,
    val memberName: String,
    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    var transactions: Set<Transaction> = emptySet()
)
