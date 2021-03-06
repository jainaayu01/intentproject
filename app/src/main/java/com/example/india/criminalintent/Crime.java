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
    private String mSuspect ;

public Crime(){
    this(UUID.randomUUID());
}
    public Crime(UUID uuid){
        mUUID = uuid;
        date = new Date();

    }
    public String getPhotoFilename(){
        return "IMG_" + getUUID().toString() + ".jpg";
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

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }
}
