package com.cardcompany.transactions.utils

import com.cardcompany.transactions.domain.entity.Account
import com.cardcompany.transactions.domain.entity.Transaction
import org.springframework.util.ResourceUtils
import java.io.IOException
import java.nio.file.Files


fun aTransactionWith(account: Account,
                     date: Long,
                     transactionId: Long,
                     merchantName: String = "Amazon",
                     summary: String = "TDD By Example"
): Transaction {
    return Transaction(
        transactionId = transactionId,
        date = date,
        amount = 50.00,
        merchantName = merchantName,
        summary = summary,
        account = account
    )
}

@Throws(IOException::class)
fun readFile(fileName: String): String {
    val file = ResourceUtils.getFile("classpath:$fileName")
    return String(Files.readAllBytes(file.toPath()))
}