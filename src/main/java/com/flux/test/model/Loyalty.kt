package com.flux.test.model
import com.flux.test.ImplementMe

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val accounts = mutableListOf<Account>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {

        val merchantSchemes = this.getMerchantSchemes(receipt.merchantId)

        for (scheme in merchantSchemes) {
            var account = this.getAccount(receipt, scheme)
            this.accounts.add(account)
        }

//        return responses

        return listOf(ApplyResponse(accounts.first().id, 1, 1, mutableListOf()))
    }

    private fun getMerchantSchemes(merchantId: MerchantId): List<Scheme> {
        return schemes.filter { s -> s.merchantId == merchantId }
    }
}