package com.flux.test.model

import java.util.*

data class Stamp (
    val id: stampId,
    val sku: String,
    val amount: Long,
    val schemeId: SchemeId,
    val receiptId: ReceiptId,
    var claimed: Boolean = false
)