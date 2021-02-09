/*
 * Copyright (C) 2014-2021 The BlissRoms Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.paranoid.paranoidsettings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;

import org.paranoid.paranoidsettings.fragments.EdgeLightSettings;

@SearchIndexable(forTarget = SearchIndexable.ALL & ~SearchIndexable.ARC)
public class Paranoid extends SettingsPreferenceFragment
                implements Preference.OnPreferenceChangeListener {

    private static final String PULSE_ON_NEW_TRACKS = "pulse_on_new_tracks";

    private SwitchPreference mPulseOnTracks;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.paranoid);

        ContentResolver resolver = getContext().getContentResolver();

        mPulseOnTracks = (SwitchPreference) findPreference(PULSE_ON_NEW_TRACKS);
        boolean pulseonTracks = Settings.Secure.getIntForUser(resolver,
                Settings.Secure.PULSE_ON_NEW_TRACKS, 0, UserHandle.USER_CURRENT) != 0;
        mPulseOnTracks.setChecked(pulseonTracks);
        mPulseOnTracks.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getContext().getContentResolver();

        if (preference == mPulseOnTracks) {
            boolean val = (Boolean) newValue;
            Settings.Secure.putIntForUser(resolver,
                Settings.Secure.PULSE_ON_NEW_TRACKS, val ? 1 : 0, UserHandle.USER_CURRENT);
            boolean pulseonTracks = Settings.Secure.getIntForUser(resolver,
                Settings.Secure.PULSE_ON_NEW_TRACKS, 0, UserHandle.USER_CURRENT) != 0;
            return true;
        }
        return false;
    }

    public static void reset(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Settings.Secure.putIntForUser(resolver,
                Settings.Secure.QS_SHOW_BRIGHTNESS_SLIDER, 1, UserHandle.USER_CURRENT);
        Settings.Secure.putIntForUser(resolver,
                Settings.Secure.QS_BRIGHTNESS_SLIDER_POSITION, 0, UserHandle.USER_CURRENT);
        EdgeLightSettings.reset(mContext);
        Settings.Secure.putIntForUser(resolver,
                Settings.Secure.PULSE_ON_NEW_TRACKS, 0, UserHandle.USER_CURRENT);
    }

    @Override
    public int getMetricsCategory() {
        return 0;
    }

    /**
     * For Search.
     */

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.paranoid);
}
