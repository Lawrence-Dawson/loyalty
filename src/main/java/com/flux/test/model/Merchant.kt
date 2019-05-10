package com.flux.test.model

class Merchant(val id: MerchantId) {
    val schemeAccounts = mutableListOf<SchemeAccount>()

    fun getSchemeAccount(scheme: Scheme, receipt: Receipt): SchemeAccount {
        val accounts = schemeAccounts.filter { sA ->
            sA.schemeId == scheme.id && sA.accountId == receipt.accountId
        }

        if (accounts.isNotEmpty()) return accounts.first()

        return this.createSchemeAccount(scheme, receipt.accountId)
    }

    private fun createSchemeAccount(scheme: Scheme, accountId: AccountId): SchemeAccount {
        val schemeAccount = SchemeAccount(scheme.id, accountId)

        schemeAccounts.add(schemeAccount)

        return schemeAccount
    }
}