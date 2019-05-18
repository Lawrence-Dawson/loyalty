package com.flux.test.model
import com.flux.test.ImplementMe
import com.flux.test.services.AccountService
import com.flux.test.services.ReceiptService

class Loyalty(schemes: List<Scheme>) : ImplementMe {
    override var schemes = schemes
    val accounts = mutableListOf<Account>()

    override fun apply(receipt: Receipt): List<ApplyResponse> {

        val applicableSchemes = this.getSchemes(receipt.merchantId)

        applicableSchemes.forEach {
            var account = AccountService().getAccount(receipt.accountId)
            account = ReceiptService().applyReceipt(account, it, receipt)
            println(account)
        }



//        return responses

        return listOf(ApplyResponse(accounts.first().id, 1, 1, mutableListOf()))
    }

    private fun getSchemes(merchantId: MerchantId): List<Scheme> {
        return schemes.filter { s -> s.merchantId == merchantId }
    }
}