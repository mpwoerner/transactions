package com.cardcompany.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionsApiApplication

fun main(args: Array<String>) {
	runApplication<TransactionsApiApplication>(*args)
}
