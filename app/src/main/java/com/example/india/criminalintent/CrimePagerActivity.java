package com.example.india.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity{
   private ViewPager mViewPager;

    private static  final String EXTRA_CRIME_ID = "crime_id";
      public static Intent newIntent(Context context,UUID uuid){
          Intent intent = new Intent(context,CrimePagerActivity.class);
          intent.putExtra(EXTRA_CRIME_ID,uuid);
          return intent;
      }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID uuid = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new CrimePagerAdapter(fragmentManager,this));
        for(int i=0;i<CrimeLab.get(this).getCrimes().size();i++){
            if(CrimeLab.get(this).getCrimes().get(i).equals(EXTRA_CRIME_ID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
