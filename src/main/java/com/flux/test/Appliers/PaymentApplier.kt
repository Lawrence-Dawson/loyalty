package com.flux.test.Appliers

import com.flux.test.model.*

class PaymentApplier(account: Account, scheme: Scheme, receipt: Receipt) : Applier(account, scheme, receipt) {
    var applications = mutableListOf<Payment>()

    override fun setApplications() {
        val stamps = this.getAccountStamps()
        if (this.hasTooManyStamps(stamps)) {
            var cheapestStamp = stamps.minBy { s -> s.amount }

            this.applications.add(
                Payment(
                    cheapestStamp!!.amount,
                    this.scheme.id,
                    this.receipt.id
                )
            )

            this.account.stamps.removeAll { s ->
                s.id == cheapestStamp.id
            }

            this.setApplications()
        }
    }

    override fun applyApplications() {
        this.applications.forEach { a ->
            this.account.payments.add(a)
        }
    }

    private fun getAccountStamps(): List<Stamp> {
        return this.account.stamps.filter { s ->
            s.schemeId == this.scheme.id
        }
    }

    private fun hasTooManyStamps(stamps: List<Stamp>): Boolean {
        return stamps.count() > this.scheme.maxStamps
    }

}
