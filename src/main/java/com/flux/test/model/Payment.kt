package com.flux.test.model

class Payment (
    val id: paymentId,
    val amount: Long,
    val schemeId: SchemeId,
    val receiptId: ReceiptId
)