/*
 * SPDX-FileCopyrightText: 2024 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.settings.security.screenlock;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public abstract class AbstractPatternSwitchPreferenceController
        extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private final String mKey;
    private final int mUserId;
    private final LockPatternUtils mLockPatternUtils;

    public AbstractPatternSwitchPreferenceController(Context context, String key,
            int userId, LockPatternUtils lockPatternUtils) {
        super(context);
        mKey = key;
        mUserId = userId;
        mLockPatternUtils = lockPatternUtils;
    }

    @Override
    public boolean isAvailable() {
        return isPatternLock();
    }

    @Override
    public String getPreferenceKey() {
        return mKey;
    }

    @Override
    public void updateState(Preference preference) {
        ((TwoStatePreference) preference).setChecked(isEnabled(mLockPatternUtils, mUserId));
    }

    private boolean isPatternLock() {
        return mLockPatternUtils.getCredentialTypeForUser(mUserId)
                == LockPatternUtils.CREDENTIAL_TYPE_PATTERN;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        setEnabled(mLockPatternUtils, mUserId, (Boolean) newValue);
        return true;
    }

    protected abstract boolean isEnabled(LockPatternUtils utils, int userId);
    protected abstract void setEnabled(LockPatternUtils utils, int userId, boolean enabled);
}
