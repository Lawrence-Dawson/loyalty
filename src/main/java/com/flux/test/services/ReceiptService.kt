package com.flux.test.services

import com.flux.test.Appliers.PaymentApplier
import com.flux.test.Appliers.StampApplier
import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class ReceiptService() {

    fun applyReceipt(account: Account, scheme: Scheme, receipt: Receipt): Account {
        var account = StampApplier(account, scheme, receipt)
            .execute()

        account = PaymentApplier(account, scheme, receipt)
            .execute()

        return account
    }

}