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

        applicableSchemes.forEach { s ->
            var account = this.accountService.getAccount(receipt.accountId)
            account = ReceiptApplication(account, s, receipt).execute()
            responses.add(
                ApplyResponse(
                    s.id,
                    account.getStampCount(s),
                    account.getStampCount(receipt),
                    account.getPayments(receipt)
                )
            )
        }



        return responses
    }

    private fun getSchemes(merchantId: MerchantId): List<Scheme> {
        return this.schemes.filter { s -> s.merchantId == merchantId }
    }
}