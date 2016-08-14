package com.example.india.criminalintent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by india on 8/10/2016.
 */
public class CrimePagerAdapter extends FragmentStatePagerAdapter {
    private List<Crime> mCrimes;
    public CrimePagerAdapter(FragmentManager manager,Context context){
        super(manager);
        mCrimes = CrimeLab.get(context).getCrimes();

    }
    @Override
    public int getCount() {
        return mCrimes.size();
    }

    @Override
    public Fragment getItem(int position) {
       Crime crime = mCrimes.get(position);
        return CrimeFragment.newInstance(crime.getUUID());
    }
}
