package com.example.india.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by india on 8/8/2016.
 */
//singleton class - private constructor and get() method
public class CrimeLab {
    private static CrimeLab sCrimeLab;
private List<Crime> mCrimes;
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for(int i=0;i<=100;i++)
        {
            Crime crime = new Crime();
            crime.setTitle("Crime#"+i);
                crime.setSolved((i%2)==0);
            mCrimes.add(crime);
        }
    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getUUID().equals(id)){
                return crime;
            }
        }
        return  null;
    }
}