package com.flux.test.services

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class ReceiptService(var account: Account, val scheme: Scheme) {

    fun applyReceipt(receipt: Receipt): Account {
        return this.account
    }

}