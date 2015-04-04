/*
 * SPDX-FileCopyrightText: 2024 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.settings.security.screenlock;

import android.content.Context;

import com.android.internal.widget.LockPatternUtils;

public class PatternErrorVisiblePreferenceController
        extends AbstractPatternSwitchPreferenceController {
    private static final String PREF_KEY = "visible_error_pattern";

    public PatternErrorVisiblePreferenceController(Context context, int userId,
            LockPatternUtils lockPatternUtils) {
        super(context, PREF_KEY, userId, lockPatternUtils);
    }

    @Override
    protected boolean isEnabled(LockPatternUtils utils, int userId) {
        return utils.isShowErrorPath(userId);
    }

    @Override
    protected void setEnabled(LockPatternUtils utils, int userId, boolean enabled) {
        utils.setShowErrorPath(enabled, userId);
    }
}
