package se.umu.chlu0125.inscriber.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import se.umu.chlu0125.inscriber.R;
/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description:
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {


    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // create first fragment
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
