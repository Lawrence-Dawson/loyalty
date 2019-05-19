package com.flux.test.model
import com.flux.test.ImplementMe

class Account(val id: AccountId) {
    private var stamps = mutableListOf<Stamp>()
    private var payments = mutableListOf<Payment>()

    fun getStampCount(receipt: Receipt): Int {
        return this.stamps.filter { s ->
            s.receiptId == receipt.id &&
            s.status != "paid"
        }.count()
    }

    fun getStampCount(scheme: Scheme): Int {
        return this.stamps.filter { s ->
            s.schemeId == scheme.id && s.status == "active"
        }.count()
    }

    fun getStamps(scheme: Scheme): List<Stamp> {
        return this.stamps.filter { s ->
            s.schemeId == scheme.id
        }
    }

    fun getStamps(receipt: Receipt): List<Stamp> {
        return this.stamps.filter { s ->
            s.receiptId == receipt.id
        }
    }

    fun getPayments(receipt: Receipt): List<Long> {
         return this.payments.filter { p ->
            p.receiptId == receipt.id
        }.map { p -> p.amount }.toList()
    }

    fun getPayments(scheme: Scheme): List<Long> {
        return this.payments.filter { p ->
            p.schemeId == scheme.id
        }.map { p -> p.amount }.toList()
    }

    fun getCheapestStamp(scheme: Scheme): Stamp? {
        val stamps = this.getStamps(scheme)
        return stamps.minBy { s -> s.amount }
    }

    fun setStampsInactive(scheme: Scheme) {
        this.getStamps(scheme).forEach { s ->
            s.status = "inactive"
        }
    }

    fun addStamp(stamp: Stamp) {
        this.stamps.add(stamp)
    }

    fun addPayment(payment: Payment) {
        this.payments.add(payment)
    }

    fun setStampPaid(stamp: Stamp) {
        this.stamps.removeAll { s ->
            s.id == stamp.id
        }
        stamp.status = "paid"
        this.stamps.add(stamp)
    }

    fun isFirstApplication(scheme: Scheme, receipt: Receipt): Boolean {
        return !this.getStamps(scheme).any { s ->
            s.receiptId == receipt.id
        }
    }

    fun isNotAppliedToOtherScheme(receipt: Receipt, scheme: Scheme, item: Item): Boolean {
        return !this.getStamps(receipt).any { s ->
            s.receiptId == receipt.id &&
            s.sku == item.sku &&
            s.schemeId != scheme.id
        }
    }

    fun hasTooManyStamps(scheme: Scheme): Boolean {
        return this.getStampCount(scheme) > scheme.maxStamps
    }
}