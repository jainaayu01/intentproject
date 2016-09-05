package com.example.india.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by india on 8/7/2016.
 */
public class CrimeFragment  extends Fragment /*implements TextWatcher*/{
    private static final  String ARG_CRIME_ID = "crime_iD";
private static final String DIALOG_DATE = "DialogDate";
    private  static final  int REQUEST_DATE=0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mCrimeSolved;
    private Button mReportButton;
    private Button mSuspectButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
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
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
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
        mReportButton = (Button)view.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getCrimeReport());
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_subject));
                intent= Intent.createChooser(intent,getString(R.string.send_report));
                startActivity(intent);
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

       //the line which is written in commments below is used to check when no  desired app is available then our app is working fine or not
        // pickContact.addCategory(Intent.CATEGORY_HOME);
        mSuspectButton = (Button)view.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });
        if(mCrime.getSuspect()!=null){
            mSuspectButton.setText(mCrime.getSuspect());
        }
        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null){
            mSuspectButton.setEnabled(false);
        }
        mPhotoButton = (ImageButton)view.findViewById(R.id.crime_camera);
        mPhotoView=(ImageView)view.findViewById(R.id.crime_photo);
        updatePhotoView();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile!=null && captureImage.resolveActivity(packageManager)!=null;
        if(canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage,REQUEST_PHOTO);
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
        else if(requestCode == REQUEST_CONTACT && data!=null){
            Uri contactUri =data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            Cursor c = getActivity().getContentResolver().query(contactUri,queryFields,null,null,null);
            try{
                if(c.getCount() ==0){
                    return;
                }
                c.moveToFirst();
                String suspect =  c.getString(0);
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            }finally{
                c.close();
            }
        }
        else if(requestCode == REQUEST_PHOTO){
            updatePhotoView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    private String getCrimeReport(){
        String solvedString = null;
        if(mCrime.getSolved()){
            solvedString = getString(R.string.crime_report_solved);
        }
        else{
            solvedString =  getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE MMM dd";
        String dateString = DateFormat.format(dateFormat,mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();
        if(suspect == null){
            suspect=getString(R.string.crime_report_no_suspect);
        }
        else{
            suspect = getString(R.string.crime_report_suspect,suspect);
        }
        String report = getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);
        return report;
    }
    private void updatePhotoView(){
        if(mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
        }
        else{
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
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
