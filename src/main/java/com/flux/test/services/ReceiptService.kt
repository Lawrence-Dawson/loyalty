package com.flux.test.services

import com.flux.test.Appliers.PaymentApplication
import com.flux.test.Appliers.StampsApplication
import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class ReceiptService() {

    fun applyReceipt(account: Account, scheme: Scheme, receipt: Receipt): Account {
        var account = StampsApplication(account, scheme).apply(receipt)
        var account = PaymentApplication(account, scheme).apply(receipt)

        return account
    }

}