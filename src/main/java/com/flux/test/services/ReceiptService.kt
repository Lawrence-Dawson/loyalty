package com.flux.test.services

import com.flux.test.Applicators.PaymentApplicator
import com.flux.test.Applicators.StampsApplicator
import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class ReceiptService() {

    fun applyReceipt(account: Account, scheme: Scheme, receipt: Receipt): Account {
        var account = StampsApplicator(account, scheme)
            .apply(receipt)

        account = PaymentApplicator(account, scheme)
            .apply(receipt)

        return account
    }

}