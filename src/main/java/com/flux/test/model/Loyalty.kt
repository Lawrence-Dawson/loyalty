package com.flux.test.model
import com.flux.test.ImplementMe
import com.flux.test.services.AccountService
import com.flux.test.services.ReceiptService

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val accountService = AccountService()
    val receiptService = ReceiptService()

    override fun apply(receipt: Receipt): List<ApplyResponse> {

        val applicableSchemes = this.getSchemes(receipt.merchantId)
        val responses = mutableListOf<ApplyResponse>()

        applicableSchemes.forEach { s ->
            var account = this.accountService.getAccount(receipt.accountId)
            account = this.receiptService.applyReceipt(account, s, receipt)
            responses.add(
                ApplyResponse(
                    s.id,
                    account.stamps.count(),
                    account.getStamps(receipt),
                    account.getPayments(receipt)
                )
            )
        }



        return responses

//        return listOf(ApplyResponse(this.schemes.first().id, 1, 1, mutableListOf()))
    }

    private fun getSchemes(merchantId: MerchantId): List<Scheme> {
        return this.schemes.filter { s -> s.merchantId == merchantId }
    }
}