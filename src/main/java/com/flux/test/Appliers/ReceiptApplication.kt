package com.flux.test.Appliers

import com.flux.test.model.*
import java.util.*

class ReceiptApplication(var account: Account, val scheme: Scheme, val receipt: Receipt) {

    fun execute() : Account {
        if (this.account.isFirstApplication(this.scheme, this.receipt)) {
            val items = this.getValidItems()
            items.forEach { item ->
                if (this.account.isNotAppliedToOtherScheme(receipt, this.scheme, item)) {
                    this.addStamp(item)
                    this.addAnyPayment()
                }
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

        if (this.account.hasTooManyStamps(this.scheme)) {
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

    private fun getValidItems(): List<Item> {
        return this.receipt.items.filter { i ->
            this.scheme.skus.contains(i.sku)
        }
    }
}