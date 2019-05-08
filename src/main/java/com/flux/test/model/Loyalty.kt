package com.flux.test.model
import com.flux.test.ImplementMe

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes

    override fun apply(receipt: Receipt): List<ApplyResponse> {
        val scheme = this.schemes.first()
        return listOf(ApplyResponse(scheme.id, 1, 1, listOf()))
    }
}