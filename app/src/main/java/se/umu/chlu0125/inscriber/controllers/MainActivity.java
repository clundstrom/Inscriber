package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import se.umu.chlu0125.inscriber.R;
import se.umu.chlu0125.inscriber.models.Inscription;


/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Main entry of application.
 * Initializes backend-related queries and sets up connection to db.
 */
public class MainActivity extends AppCompatActivity {

    private static InscriptionService mService;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = new InscriptionService();

        // Async scope
        mService.getUserDataTask().addOnSuccessListener((snapshot) -> {
            Inscription ins = snapshot.toObject(Inscription.class);
            Log.d(TAG, "onCreate: "+ ins.getMessage());
        });


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
}
