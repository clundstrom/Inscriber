package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import se.umu.chlu0125.inscriber.R;
import se.umu.chlu0125.inscriber.models.Inscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static FirebaseInstanceId mInstanceId;
    private String mIdToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InscriptionService service = new InscriptionService();

        //Inscription s = new Inscription();
        //service.setUserData(s);


        // Async scope
        service.getUserDataTask().addOnSuccessListener((snapshot) -> {
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
