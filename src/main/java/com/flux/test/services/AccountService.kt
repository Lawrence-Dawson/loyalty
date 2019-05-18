package com.flux.test.services

import com.flux.test.model.Account
import com.flux.test.model.AccountId

class AccountService() {
    var accounts = mutableListOf<Account>()

    fun getAccount(accountId: AccountId): Account {
        var account = accounts
                .stream()
                .filter({ a -> a.id == accountId })
                .findFirst()
                .orElse(null)

        if (account == null) account = Account(accountId)

        return account
    }
}