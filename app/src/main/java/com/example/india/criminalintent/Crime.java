package com.example.india.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by india on 8/7/2016.
 */
public class Crime {
    private UUID mUUID;
    private String mTitle;
    private Date date;
    private Boolean mSolved;


    public Crime(){
        mUUID = UUID.randomUUID();
        date = new Date();

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSolved(Boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Boolean getSolved() {
        return mSolved;
    }

    public Date getDate() {
        return date;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
