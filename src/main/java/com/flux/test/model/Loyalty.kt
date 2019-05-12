package com.flux.test.model
import com.flux.test.ImplementMe

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val responses = mutableListOf<ApplyResponse>()
    val accounts = mutableListOf<Account>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {

        val merchantSchemes = this.getMerchantSchemes(merchant)

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

    private fun getAccount(receipt: Receipt, scheme: Scheme): Account {
        val matches = accounts.filter { a ->
            a.schemeId == scheme.id && a.id == receipt.accountId
        }

        if (matches.isNotEmpty()) return matches.first()
        return Account(receipt.accountId, scheme.id, 0, mutableListOf())
    }
}