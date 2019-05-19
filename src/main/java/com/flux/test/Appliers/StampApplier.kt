package com.flux.test.Appliers

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

class StampApplier(account: Account, scheme: Scheme, receipt: Receipt) : Applier(account, scheme, receipt) {
    override var applications = mutableListOf<Applier>()

    override fun setApplications() {

    }

    override fun applyApplications() {

    }
}
