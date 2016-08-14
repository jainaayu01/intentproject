package com.example.india.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by india on 8/9/2016.
 */
public class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Crime mCrime;
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private CheckBox mSolvedCheckBox;
    private Context mContext;
    public CrimeHolder(View itemView) {
        super(itemView);
        // mTitleTextView = (TextView)itemView;
        itemView.setOnClickListener(this);
        mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
        mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
        mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
    }
    public void bindCrime(Crime crime,Context context){
        mCrime = crime;
        mContext = context;
        mTitleTextView.setText(mCrime.getTitle());
        mDateTextView.setText(mCrime.getDate().toString());
        mSolvedCheckBox.setEnabled(mCrime.getSolved());
    }

    @Override
    public void onClick(View view) {
       // Toast.makeText(mContext,mCrime.getTitle()+"clicked!",Toast.LENGTH_SHORT).show();
        Intent intent = CrimePagerActivity.newIntent(mContext,mCrime.getUUID());
        mContext.startActivity(intent);
    }
}

