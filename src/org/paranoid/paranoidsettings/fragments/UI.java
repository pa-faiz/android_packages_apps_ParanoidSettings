package org.paranoid.paranoidsettings.fragments;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.Toast;
import android.os.SystemProperties;
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import org.paranoid.paranoidsettings.fragments.IslandSettings;


public class UI extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    public static void reset(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Settings.System.putIntForUser(resolver,
                Settings.System.CUSTOM_VOLUME_STYLES, 2, UserHandle.USER_CURRENT);
        IslandSettings.reset(mContext);
    }

    @Override
    public int getMetricsCategory() {
        return 0;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.ui_settings, rootKey);

        getActivity().setTitle(R.string.something_ui_dashboard_title);

        SwitchPreference enableAdblock = (SwitchPreference) findPreference("enable_adblock");
        if (enableAdblock != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
            boolean enableAdblockValue = prefs.getBoolean("enable_adblock", false);
            enableAdblock.setChecked(enableAdblockValue);

            enableAdblock.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isChecked = (Boolean) newValue;
                prefs.edit().putBoolean("enable_adblock", isChecked).apply();

                if (isChecked) {
                    Settings.Global.putString(
                        getContext().getContentResolver(),
                        Settings.Global.PRIVATE_DNS_MODE,
                        "hostname"
                    );
                    Settings.Global.putString(
                        getContext().getContentResolver(),
                        Settings.Global.PRIVATE_DNS_SPECIFIER,
                        "dns.adguard.com"
                    );
                } else {
                    Settings.Global.putString(
                        getContext().getContentResolver(),
                        Settings.Global.PRIVATE_DNS_MODE,
                        "off"
                    );
                }

                return true;
            });
        }
    }
}
