package com.flux.test.model
import com.flux.test.ImplementMe

class LoyaltyOld(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val merchants = mutableListOf<Merchant>()
    val applyResponse = mutableListOf<ApplyResponse>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {
        val merchant = this.getMerchant(receipt.merchantId)
        val merchantSchemes = this.getMerchantSchemes(merchant)

        merchantSchemes.forEach { scheme ->
            this.applyReceipt(scheme, merchant, receipt)
        }

        return this.applyResponse
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
        val skuMap = receipt.items.groupBy { i -> i.sku }
        var schemeAccount = merchant.getSchemeAccount(scheme, receipt)

        skuMap.forEach { (key, items) ->
            val sku = items.first()
            var stampCount = items.count()

            schemeAccount.currentStampCount += stampCount

            if (schemeAccount.currentStampCount >= scheme.maxStamps) {
                stampCount -= 1
                val cheapestItem = items.minBy { i -> i.price }
                schemeAccount.currentStampCount = 0
                schemeAccount.paymentsGiven.add(cheapestItem!!.price)
            }

            this.applyResponse.add(
                    ApplyResponse(scheme.id,
                    schemeAccount.currentStampCount,
                    stampCount,
                    schemeAccount.paymentsGiven)
            )
        }
    }


}