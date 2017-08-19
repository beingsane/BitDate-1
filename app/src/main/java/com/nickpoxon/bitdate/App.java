package com.nickpoxon.bitdate;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by nickpoxon on 06/08/2017.
 */

public class App extends Application {

    private FirebaseAuth mFirebaseAuth;

    private static final String TAG = "App";

    @Override
    public void onCreate(){
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        //databaseReference = database.getReference();
    }
}
