package com.cardcompany.transactions.controller

import com.cardcompany.transactions.domain.exception.AccountNotFoundException
import com.cardcompany.transactions.domain.exception.Error
import com.cardcompany.transactions.domain.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TransactionsControllerAdvice {

    @ExceptionHandler(value = [AccountNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAccountNotFoundException(ex: Exception): ErrorResponse {
        val type = ex.javaClass.simpleName
        val message = ex.message ?: "Account with given ID could not be found"
        return ErrorResponse(
            error = Error(
                type = type,
                message = message
            )
        )
    }
}