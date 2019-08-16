package se.umu.chlu0125.inscriber.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import se.umu.chlu0125.inscriber.R;


/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Main entry of application.
 * Initializes backend-related queries and sets up connection to db.
 */
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Fragment mDialog;
    private Fragment mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        mTabManager = fm.findFragmentById(R.id.fragment_container);
        mDialog = fm.findFragmentById(R.id.fragment_container);

        if(savedInstanceState != null){
            mTabManager = fm.getFragment(savedInstanceState, "TAB_MANAGER");
        }

        if (mTabManager == null) {
            mTabManager = TabManagerFragment.newInstance();
            mDialog = GuideDialogFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mTabManager)
                    .add(R.id.fragment_container, mDialog)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "TAB_MANAGER", mTabManager);
    }
}
