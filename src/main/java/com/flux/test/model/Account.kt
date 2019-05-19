package com.flux.test.model
import com.flux.test.ImplementMe

class Account(val id: AccountId) {
    private var stamps = mutableListOf<Stamp>()
    private var payments = mutableListOf<Payment>()

    fun getStampCount(receipt: Receipt): Int {
        return this.stamps.filter { s ->
            s.receiptId == receipt.id
        }.count()
    }

    fun getStampCount(scheme: Scheme): Int {
        return this.stamps.filter { s ->
            s.schemeId == scheme.id && !s.claimed
        }.count()
    }

    fun getStamps(scheme: Scheme): List<Stamp> {
        return this.stamps.filter { s ->
            s.schemeId == scheme.id
        }
    }

    fun getPayments(receipt: Receipt): List<Long> {
         return this.payments.filter { p ->
            p.receiptId == receipt.id
        }.map { p -> p.amount }.toList()
    }

    fun getCheapestStamp(scheme: Scheme): Stamp? {
        val stamps = this.getStamps(scheme)
        return stamps.minBy { s -> s.amount }
    }

    fun setStampsApplied(scheme: Scheme) {
        this.getStamps(scheme).forEach { s ->
            s.claimed = true
        }
    }

    fun addStamp(stamp: Stamp) {
        this.stamps.add(stamp)
    }

    fun addPayment(payment: Payment) {
        this.payments.add(payment)
    }

    fun removeStamp(stamp: Stamp) {
        this.stamps.removeAll { s ->
            s.id == stamp.id
        }
    }

    fun isFirstApplication(scheme: Scheme, receipt: Receipt): Boolean {
        return !this.getStamps(scheme).any { s ->
            s.receiptId == receipt.id
        }
    }
}