package com.flux.test.Applicators

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

abstract class Applicator(var account: Account, val scheme: Scheme) {

    fun apply(receipt: Receipt) : Account {
        return this.account
    }
}