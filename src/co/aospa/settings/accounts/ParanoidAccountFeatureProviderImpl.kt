/*
 * SPDX-FileCopyrightText: 2025 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.settings.accounts

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.android.settings.R
import com.android.settings.accounts.AccountFeatureProvider

/** Feature provider implementation for accounts. */
class ParanoidAccountFeatureProviderImpl : AccountFeatureProvider {

    override fun getAccountType(): String = ACCOUNT_TYPE_GOOGLE

    override fun getAccounts(context: Context): Array<Account> =
        AccountManager.get(context).getAccountsByType(getAccountType())

    companion object {
        private const val ACCOUNT_TYPE_GOOGLE = "com.google"
    }
}
