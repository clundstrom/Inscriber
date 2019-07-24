package se.umu.chlu0125.inscriber.controllers;

import android.support.v4.app.Fragment;
import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description:
 */
public class GuideActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return new GuideFragment();
    }
}
