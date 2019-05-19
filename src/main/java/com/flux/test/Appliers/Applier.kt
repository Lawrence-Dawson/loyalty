package com.flux.test.Appliers

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

abstract class Applier(var account: Account, val scheme: Scheme, val receipt: Receipt) {

    fun execute() : Account {
        this.setApplications()
        this.applyApplications()

        return this.account
    }

    abstract fun setApplications()
    abstract fun applyApplications()
}