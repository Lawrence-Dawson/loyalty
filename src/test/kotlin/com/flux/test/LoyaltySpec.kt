package com.flux.test

import com.flux.test.model.*
import io.kotlintest.IsolationMode
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.UUID

class LoyaltySpec : StringSpec() {

    val implementation: ImplementMe = Loyalty(schemes)

    init {
        "Applies a stamp" {
            val receipt = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))

            val response = implementation.apply(receipt)

            response shouldHaveSize (1)
            response.first().stampsGiven shouldBe 1
            response.first().currentStampCount shouldBe 1
            response.first().paymentsGiven shouldHaveSize 0
        }

        "Triggers a redemption" {
            val receipt =
                Receipt(merchantId = merchantId, accountId = accountId, items = 1.rangeTo(5).map { Item("1", 100, 1) })
            val response = implementation.apply(receipt)

            response shouldHaveSize (1)
            response.first().stampsGiven shouldBe 4
            response.first().currentStampCount shouldBe 0
            response.first().paymentsGiven shouldHaveSize 1
            response.first().paymentsGiven.first() shouldBe 100
        }

        "Stores the current state for an account" {
            val receipt = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))

            implementation.apply(receipt)
            val response = implementation.state(accountId)

            response shouldHaveSize (1)
            response.first().currentStampCount shouldBe 1
            response.first().payments shouldHaveSize 0
        }

        "Multiple schemes can be running for the same merchant" {
            val schemes = listOf<Scheme>(
                Scheme(schemeId, merchantId, 4, listOf("1")),
                Scheme(schemeId, merchantId, 4, listOf("2")),
                Scheme(schemeId, merchantId, 4, listOf("3")),
                Scheme(schemeId, merchantId, 4, listOf("4"))
            )

            val implementation: ImplementMe = Loyalty(schemes)

            var items = listOf(
                Item("1", 100, 1),
                Item("2", 200, 1),
                Item("3", 300, 1),
                Item("4", 400, 1)
            )

            val receipt = Receipt(merchantId = merchantId, accountId = accountId, items = items)

            implementation.apply(receipt)
            val response = implementation.state(accountId)

            response shouldHaveSize (4)
            response.forEach { r ->
                r.currentStampCount shouldBe 1
            }
            response.forEach { r ->
                r.payments shouldHaveSize 0
            }
        }

        "Applies multiple receipts" {
            val receipt1 = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))
            val receipt2 = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))
            val receipt3 = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))

            implementation.apply(receipt1)
            implementation.apply(receipt2)
            val response = implementation.apply(receipt3)

            response shouldHaveSize (1)
            response.first().stampsGiven shouldBe 1
            response.first().currentStampCount shouldBe 3
            response.first().paymentsGiven shouldHaveSize 0
        }

        "Cheapest item in scheme given away in redemption" {
            val schemes = listOf<Scheme>(
                Scheme(schemeId, merchantId, 2, listOf("1", "2", "3"))
            )

            val implementation: ImplementMe = Loyalty(schemes)

            var items = listOf(
                Item("1", 100, 1),
                Item("2", 50, 1),
                Item("3", 300, 1)
            )

            val receipt =
                Receipt(merchantId = merchantId, accountId = accountId, items = items)

            val response = implementation.apply(receipt)

            response shouldHaveSize (1)
            response.first().stampsGiven shouldBe 2
            response.first().currentStampCount shouldBe 0
            response.first().paymentsGiven shouldHaveSize 1
            response.first().paymentsGiven.first() shouldBe 50
        }

        "Each item in a receipt can be used only once" {
            val receipt = Receipt(merchantId = merchantId, accountId = accountId, items = listOf(Item("1", 100, 1)))

            implementation.apply(receipt)
            implementation.apply(receipt)

            val response = implementation.state(accountId)

            response shouldHaveSize (1)
            response.first().currentStampCount shouldBe 1
            response.first().payments shouldHaveSize 0
        }

        "item cannot be used in multiple schemes" {
            val schemeId2 = UUID.randomUUID()
            val schemes = listOf<Scheme>(
                Scheme(schemeId, merchantId, 4, listOf("1")),
                Scheme(schemeId2, merchantId, 2, listOf("1"))
            )

            val implementation: ImplementMe = Loyalty(schemes)

            var items = listOf(
                Item("1", 100, 1)
            )

            val receipt =
                Receipt(merchantId = merchantId, accountId = accountId, items = items)

            implementation.apply(receipt)

            val response = implementation.state(accountId)
  
            response shouldHaveSize (2)

            response.first().currentStampCount shouldBe 1
            response.first().payments shouldHaveSize 0
            response.last().currentStampCount shouldBe 0
            response.last().payments shouldHaveSize 0
        }
    }

    override fun isolationMode() = IsolationMode.InstancePerTest

    companion object {
        private val accountId: AccountId = UUID.randomUUID()
        private val merchantId: MerchantId = UUID.randomUUID()

        private val schemeId: SchemeId = UUID.randomUUID()
        private val schemes = listOf(Scheme(schemeId, merchantId, 4, listOf("1")))
    }

}