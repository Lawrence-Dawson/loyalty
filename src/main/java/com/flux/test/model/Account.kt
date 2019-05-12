package com.flux.test.model
import com.flux.test.ImplementMe

data class Account(
    val id: AccountId
    val schemeId: SchemeId,
    val currentStampCount: Int,
    val paymentsGiven: List<Long>
)