package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
 * Initializes Fragments and sets up tabmanager, toolbars and it's child-views.
 */
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Fragment mDialog;
    private Fragment mTabManager;
    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        mTabManager = fm.findFragmentById(R.id.fragment_container);
        mDialog = fm.findFragmentById(R.id.fragment_container);

        if (savedInstanceState != null) {
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

    /**
     * Responsible for creating the SettingsFragment. Makes sure there is only one
     * SettingsFragment active in the backstack.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                if (!isFragmentDuplicate("SETTINGS", SettingsFragment.class)) {
                    mSettingsFragment = new SettingsFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, mSettingsFragment, "SETTINGS")
                            .commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "TAB_MANAGER", mTabManager);

        if(isFragmentDuplicate("SETTINGS", SettingsFragment.class)){
            getSupportFragmentManager().putFragment(outState, "SETTINGS", mSettingsFragment);
        }

    }

    /**
     * Compares a Fragment to the FragmentManagers backstack to determine if there are duplicates of the Fragment-class.
     * <p>
     * Usage: isFragmentDuplicate("SETTINGS", MyCustomFragment.class)
     *
     * @param tag    String tag of a fragment.
     * @param object The object to compare with.
     * @return
     */
    private boolean isFragmentDuplicate(String tag, Object object) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f instanceof Object) {
            return true;
        } else {
            return false;
        }
    }
}
