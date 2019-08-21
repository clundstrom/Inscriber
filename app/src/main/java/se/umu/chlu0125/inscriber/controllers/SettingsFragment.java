package se.umu.chlu0125.inscriber.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 18/08/2019
 * <p>
 * Description: Responsible for handling application settings and menu interaction.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String CLEAR = "pref_clear_data";
    private static final String FEEDBACK = "pref_feedback";
    private static final String mFeedbackAddress = "contact@clundstrom.com";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));

        bindPreferences();
        return view;
    }

    /**
     * Listens for Backpress and destroys fragment if found.
     */
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                closeFragment();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }

    /**
     * Closes settings fragment.
     */
    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    /**
     * Binds actions(feedback and reset inscriptions) to Preferences.
     */
    private void bindPreferences() {

        Preference clearData = findPreference(CLEAR);
        Preference feedback = findPreference(FEEDBACK);

        feedback.setOnPreferenceClickListener((click) -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto: " + mFeedbackAddress));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
            return false;
        });
        clearData.setOnPreferenceClickListener((click) -> {
            ResetDialogFragment resetData = new ResetDialogFragment();
            resetData.setTargetFragment(this, 1);
            resetData.show(getFragmentManager(), "ResetData");
            return false;
        });
    }
}

