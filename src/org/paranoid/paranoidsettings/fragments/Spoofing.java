package org.paranoid.paranoidsettings.fragments;

import android.os.Bundle;
import android.os.SystemProperties;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class Spoofing extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return 0;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.spoofing_settings, rootKey);

        getActivity().setTitle(R.string.something_spoofing_dashboard_title);

        SwitchPreference spoofGphotosPreference = (SwitchPreference) findPreference("spoof_gphotos");
        if (spoofGphotosPreference != null) {
            String gphotosProp = SystemProperties.get("persist.sys.paranoid.gphotos", "false");
            spoofGphotosPreference.setChecked(!gphotosProp.equals("false"));

            spoofGphotosPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if ((Boolean) newValue) {
                    SystemProperties.set("persist.sys.paranoid.gphotos", "true");
                } else {
                    SystemProperties.set("persist.sys.paranoid.gphotos", "false");
                }
                return true;
            });
        }

        SwitchPreference spoofGoogleAppsPreference = (SwitchPreference) findPreference("spoof_google_apps");
        if (spoofGoogleAppsPreference != null) {
            String gappsProp = SystemProperties.get("persist.sys.paranoid.gapps", "false");
            spoofGoogleAppsPreference.setChecked(!gappsProp.equals("false"));

            spoofGoogleAppsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if ((Boolean) newValue) {
                    SystemProperties.set("persist.sys.paranoid.gapps", "true");
                } else {
                    SystemProperties.set("persist.sys.paranoid.gapps", "false");
                }
                return true;
            });
        }
    }
}
