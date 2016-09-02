package com.example.india.criminalintent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by india on 8/9/2016.
 */
public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
    private List<Crime> mCrimes;
    private Context mContext;
    public CrimeAdapter(List<Crime> crimes,Context context){
        mCrimes = crimes;
        mContext = context;
    }
    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_crime,parent,false);
        return new CrimeHolder(view);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, int position) {
        Crime crime = mCrimes.get(position);
        holder.bindCrime(crime,mContext);
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    public void setCrimes(List<Crime> crimes){
        mCrimes = crimes;
    }
}
