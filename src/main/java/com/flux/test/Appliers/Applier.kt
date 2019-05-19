package com.flux.test.Appliers

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

abstract class Applier(var account: Account, val scheme: Scheme, val receipt: Receipt) {
    abstract var applications: MutableList<Applier>

    fun execute() : Account {
        return this.account
    }

    abstract fun setApplications()
    abstract fun applyApplications()
}