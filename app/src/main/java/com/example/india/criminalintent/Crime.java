package com.example.india.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by india on 8/7/2016.
 */
public class Crime {
    private UUID mUUID;
    private String mTitle = "Default Crime";
    private Date date = new Date() ;
    private Boolean mSolved =false;

public Crime(){
    this(UUID.randomUUID());
}
    public Crime(UUID uuid){
        mUUID = uuid;
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
