package com.nickpoxon.bitdate;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.io.Serializable;

/**
 * Created by nickpoxon on 12/08/2017.
 */

public class User implements Serializable{

    private static final String TAG = "User";

    private String mDisplayName;
    private String mPictureURL;
    private String mID;

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getPictureURL() {
        return mPictureURL;
    }

    public void setPictureURL(String pictureURL) {
        mPictureURL = pictureURL;
    }

}
