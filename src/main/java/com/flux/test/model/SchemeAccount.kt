package com.flux.test.model

class SchemeAccount(val schemeId: SchemeId, val accountId: AccountId) {
    var currentStampCount: Int = 0
    var paymentsGiven = mutableListOf<Long>()
}