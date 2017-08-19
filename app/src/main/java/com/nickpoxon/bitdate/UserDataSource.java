package com.nickpoxon.bitdate;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickpoxon on 12/08/2017.
 */

public class UserDataSource {

    private static final String TAG = "UserDataSource";
    private static User sCurrentUser;
    private static DatabaseReference mRootRef;
    private static DatabaseReference mUsersRef;
    private static final String TABLE_NAME = "users";
    private static ValueEventListener sListener;

    public static User getCurrentUser(){
        if (sCurrentUser == null && FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            sCurrentUser = new User();
            sCurrentUser.setID(user.getUid());
            sCurrentUser.setDisplayName(user.getDisplayName());
            sCurrentUser.setPictureURL(user.getPhotoUrl().toString());
        }
        return sCurrentUser;
    }

    public static void getUnseenUsers(final UserDataCallbacks callback) {

        mRootRef = FirebaseDatabase.getInstance().getReference();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<User> users = new ArrayList<User>();
                for (DataSnapshot firebaseUser : dataSnapshot.child("users").getChildren()) {
                    User user = firebaseUsertoUser(firebaseUser);
                    users.add(user);
                }
                if (callback != null) {
                    callback.onUsersFetched(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mRootRef.addValueEventListener(eventListener);
    }

    public static void getUsersIn(final List<String> ids, final UserDataCallbacks callback){

        mRootRef = FirebaseDatabase.getInstance().getReference();
        sListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<User> users = new ArrayList<User>();
                for (DataSnapshot firebaseUser : dataSnapshot.child("users").getChildren()) {
                    User user = firebaseUsertoUser(firebaseUser);
                    for (int i=0; i<ids.size(); i++){
                        if (user.getID().equals(ids.get(i))){
                            users.add(user);
                        }
                    }
                }
                if (callback != null) {
                    callback.onUsersFetched(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mRootRef.addValueEventListener(sListener);
    }

    private static User firebaseUsertoUser(DataSnapshot firebaseUser) {
        User user = new User();
        user.setID(firebaseUser.getKey());
        user.setDisplayName((String)firebaseUser.child("firstName").getValue());
        user.setPictureURL((String)firebaseUser.child("pictureUrl").getValue());
        return user;
    }

    public interface UserDataCallbacks{
        public void onUsersFetched(List<User> users);
    }

    public static void stop(ValueEventListener listener){
        mRootRef.removeEventListener(listener);
    }
}
