package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.util.Log;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import se.umu.chlu0125.inscriber.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static FirebaseInstanceId mInstanceId;
    private String mIdToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInstanceId();
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Fragment dialog = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = TabManagerFragment.newInstance();
            dialog = GuideDialogFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .add(R.id.fragment_container, dialog)
                    .commit();
        }

    }

    /**
     *  Firebase Instance ID provides a unique identifier for each app instance and a mechanism to authenticate and authorize actions.
     *  Resets with app reinstalls, clearing of app-data, restores etc. More info @ firebase api.
     *  Used as unique id of user db-entry.
     */
    private void getInstanceId(){
        if (mInstanceId == null){
            Log.d(TAG, "getInstanceId: Local instanceID null.");
            mInstanceId = FirebaseInstanceId.getInstance();
            mIdToken = mInstanceId.getId();
        }
    }
}
