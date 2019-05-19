package com.flux.test.Appliers

import com.flux.test.model.*
import java.util.*

class ReceiptApplication(var account: Account, val scheme: Scheme, val receipt: Receipt) {

    fun execute() : Account {

        if (this.account.isFirstApplication(this.scheme, this.receipt)) {
            val items = this.getValidItems()

            items.forEach { i ->
                this.addStamp(i)
                this.addAnyPayment()
            }
        }
        return this.account
    }

    private fun addStamp(item: Item) {
        this.account.addStamp(
            Stamp(
                UUID.randomUUID(),
                item.sku,
                item.price,
                this.scheme.id,
                this.receipt.id
            )
        )
    }

    private fun addAnyPayment() {
        val stamps = this.account.getStamps(this.scheme)

        if (this.hasTooManyStamps(stamps)) {

            val cheapestStamp = this.account.getCheapestStamp(this.scheme)

            this.account.addPayment(
                Payment(
                    UUID.randomUUID(),
                    cheapestStamp!!.amount,
                    this.scheme.id,
                    this.receipt.id
                )
            )

            this.account.removeStamp(cheapestStamp)
            this.account.setStampsApplied(this.scheme)
        }
    }

    private fun hasTooManyStamps(stamps: List<Stamp>): Boolean {
        return stamps.count() > this.scheme.maxStamps
    }

    private fun getValidItems(): List<Item> {
        return this.receipt.items.filter { i ->
            this.scheme.skus.contains(i.sku)
        }
    }
}