package com.flux.test.model
import com.flux.test.ImplementMe

data class Account(val id: AccountId) {
    var stamps = mutableListOf<Stamp>()
    var payments = mutableListOf<Payment>()
}