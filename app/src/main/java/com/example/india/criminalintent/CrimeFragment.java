package com.example.india.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by india on 8/7/2016.
 */
public class CrimeFragment  extends Fragment /*implements TextWatcher*/{
    private static final  String ARG_CRIME_ID = "crime_iD";
private static final String DIALOG_DATE = "DialogDate";
    private  static final  int REQUEST_DATE=0;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mCrimeSolved;
      public static CrimeFragment newInstance(UUID crimeId){
          Bundle args = new Bundle();
          args.putSerializable(ARG_CRIME_ID,crimeId);
          CrimeFragment fragment = new CrimeFragment();
          fragment.setArguments(args);
          return fragment;
      }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime,container,false);
        mTitleField = (EditText)view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
       /// mTitleField.addTextChangedListener(this);
        mDateButton = (Button)view.findViewById(R.id.crime_date);
        mCrimeSolved= (CheckBox)view.findViewById(R.id.crime_solved);
        updateDate();
        // mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm =getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(fm,DIALOG_DATE);
            }
        });
        mCrimeSolved.setChecked(true);
        mCrimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
       // mTitleField.addTextChangedListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

       /* public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          mTitleField.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }*/
}
