package com.flux.test.services

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class ReceiptService() {

    fun applyReceipt(account: Account, scheme: Scheme, receipt: Receipt): Account {
        return account
    }

}