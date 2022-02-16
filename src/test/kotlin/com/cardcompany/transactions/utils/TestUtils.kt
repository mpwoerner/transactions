package com.cardcompany.transactions.utils

import org.springframework.util.ResourceUtils
import java.io.IOException
import java.nio.file.Files

@Throws(IOException::class)
fun readFile(fileName: String): String {
    val file = ResourceUtils.getFile("classpath:$fileName")
    return String(Files.readAllBytes(file.toPath()))
}