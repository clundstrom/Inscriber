package se.umu.chlu0125.inscriber.controllers;

import android.support.v4.app.Fragment;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Activity which handles instantiation of GuideFragment.
 */
public class GuideActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return GuideDialogFragment.newInstance();
    }
}
