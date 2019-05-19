package com.flux.test.Appliers

import com.flux.test.model.*
import java.util.*

class StampApplier(account: Account, scheme: Scheme, receipt: Receipt) : Applier(account, scheme, receipt) {
    var applications = mutableListOf<Stamp>()

    override fun setApplications() {
        if (this.isFirstApplication()) {
            val items = this.getValidItems()

            items.forEach { i ->
                this.applications.add(
                    Stamp(
                        UUID.randomUUID(),
                        i.sku,
                        i.price,
                        this.scheme.id,
                        this.receipt.id
                    )
                )
            }
        }
    }

    override fun applyApplications() {
        this.applications.forEach { a ->
            this.account.stamps.add(a)
        }
    }

    private fun isFirstApplication(): Boolean {
        return !this.account.stamps.any { s ->
            s.receiptId == this.receipt.id &&
            s.schemeId == this.scheme.id
        }
    }

    private fun getValidItems(): List<Item> {
        return this.receipt.items.filter { i ->
            this.scheme.skus.contains(i.sku)
        }
    }
}
