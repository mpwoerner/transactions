package com.cardcompany.transactions.domain

import javax.persistence.*

@Entity
class Transaction(
    @Id
    @Column(name = "transaction_id")
    val transactionId: Long = 0,
    val date: Long = 0,
    val amount: Double = 0.0,
    val merchantName: String? = null,
    val summary: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    val account: Account? = null
)