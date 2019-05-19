package com.flux.test.Appliers

import com.flux.test.model.Account
import com.flux.test.model.Payment
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class PaymentApplier(account: Account, scheme: Scheme, receipt: Receipt) : Applier(account, scheme, receipt) {
    var applications = mutableListOf<Payment>()

    override fun setApplications() {

    }

    override fun applyApplications() {

    }

}
