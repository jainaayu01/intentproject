package com.example.india.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by india on 8/8/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
