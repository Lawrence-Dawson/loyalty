package com.flux.test.model
import com.flux.test.Appliers.ReceiptApplication
import com.flux.test.ImplementMe
import com.flux.test.services.AccountService

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val accountService = AccountService()

    override fun apply(receipt: Receipt): List<ApplyResponse> {
        val applicableSchemes = this.getSchemes(receipt.merchantId)
        val responses = mutableListOf<ApplyResponse>()

        applicableSchemes.forEach { scheme ->
            var account = this.accountService.getAccount(receipt.accountId)

            account = ReceiptApplication(account, scheme, receipt).execute()
            accountService.updateAccount(account)

            responses.add(
                ApplyResponse(
                    scheme.id,
                    account.getCurrentStampCount(scheme),
                    account.getStampsGivenCount(receipt),
                    account.getPaymentsGiven(receipt)
                )
            )
        }

        return responses
    }

    override fun state(accountId: AccountId): List<StateResponse> {
        val responses = mutableListOf<StateResponse>()

        this.schemes.forEach { scheme ->
            val account = this.accountService.getAccount(accountId)
            responses.add(
                StateResponse(
                    scheme.id,
                    account.getCurrentStampCount(scheme),
                    account.getPayments(scheme)
                )
            )
        }

        return responses
    }

    private fun getSchemes(merchantId: MerchantId): List<Scheme> {
        return this.schemes.filter { s -> s.merchantId == merchantId }
    }
}