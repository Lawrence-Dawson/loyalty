package com.flux.test.Appliers

import com.flux.test.model.Account
import com.flux.test.model.Receipt
import com.flux.test.model.Scheme

abstract class Applier(var account: Account, val scheme: Scheme) {

    fun apply(receipt: Receipt) {

    }
}