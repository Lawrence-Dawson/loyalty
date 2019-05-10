package com.flux.test.model
import com.flux.test.ImplementMe

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val merchants = mutableListOf<Merchant>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {
        val merchant = this.getMerchant(receipt.merchantId)
        
        return listOf(ApplyResponse(schemes.first().id, 1, 1, listOf()))
    }

    private fun getMerchant(merchantId: MerchantId): Merchant {
        if (merchants.any { m -> m.id == merchantId }) {
            return merchants.filter { m -> m.id == merchantId }.first()
        }

        val merchantSchemes = schemes.filter { scheme -> scheme.merchantId == merchantId }
        val merchant = Merchant(merchantId, merchantSchemes)

        merchants.add(merchant)

        return merchant
    }


}