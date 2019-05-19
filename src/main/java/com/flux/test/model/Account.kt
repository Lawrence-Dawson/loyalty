package com.flux.test.model
import com.flux.test.ImplementMe

class Account(val id: AccountId) {
    var stamps = mutableListOf<Stamp>()
    var payments = mutableListOf<Payment>()

    fun getStamps(receipt: Receipt): Int {
        return this.stamps.filter { s ->
            s.receiptId == receipt.id
        }.count()
    }

    fun getPayments(receipt: Receipt): List<Long> {
         return this.payments.filter { p ->
            p.receiptId == receipt.id
        }.map { p -> p.amount }.toList()
    }
}