package com.cardcompany.transactions.domain

import javax.persistence.*

@Entity
class Account(
    @Id
    @Column(name = "ACCOUNT_ID")
    val accountId: Long = 0,
    val memberName: String? = null,
    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    var transactions: Set<Transaction>? = null
)
