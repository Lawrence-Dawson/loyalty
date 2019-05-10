package com.flux.test.model
import com.flux.test.ImplementMe

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val merchants = mutableListOf<Merchant>()
    val response = mutableListOf<ApplyResponse>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {
        val merchant = this.getMerchant(receipt.merchantId)
        val merchantSchemes = this.getMerchantSchemes(merchant)

        merchantSchemes.forEach { scheme -> this.applyReceipt(scheme, merchant, receipt) }

        return listOf(ApplyResponse(schemes.first().id, 1, 1, listOf()))
    }

    private fun getMerchant(merchantId: MerchantId): Merchant {
        if (merchants.any { m -> m.id == merchantId }) {
            return merchants.filter { m -> m.id == merchantId }.first()
        }

        val merchant = Merchant(merchantId)

        merchants.add(merchant)

        return merchant
    }

    private fun getMerchantSchemes(merchant: Merchant): List<Scheme> {
        return schemes.filter { s -> s.merchantId == merchant.id }
    }

    private fun applyReceipt(scheme: Scheme, merchant: Merchant, receipt: Receipt)
    {
        var schemeAccount = merchant.getSchemeAccount(scheme, receipt)
        println(schemeAccount)
    }


}