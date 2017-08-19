package com.nickpoxon.bitdate;

import android.support.annotation.NonNull;
import android.support.constraint.solver.SolverVariable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickpoxon on 15/08/2017.
 */

public class ActionDataSource {

    private static final String TAG = "ActionDataSource";
    private static final String TABLE_NAME = "actions";

    public static void saveUserLiked(final String userId){

        final DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query query = mFirebaseRef.child(TABLE_NAME).orderByChild("toUser").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Action action;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot possibleMatch : dataSnapshot.getChildren()) {
                        if (Type.Liked.getLabel().equals(possibleMatch.child("type").getValue())) {
                            Log.d(TAG, "Its a match!!!");
                            possibleMatch.getRef().child("type").setValue(Type.Matched);
                            action = createAction(userId, Type.Matched);
                            mFirebaseRef.child(TABLE_NAME).push().setValue(action);
                        } else {
                            action = createAction(userId, Type.Liked);
                            mFirebaseRef.child(TABLE_NAME).push().setValue(action);
                        }
                    }
                } else {
                    action = createAction(userId, Type.Liked);
                    mFirebaseRef.child(TABLE_NAME).push().setValue(action);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void saveUserSkipped(String userId){
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Action action = createAction(userId, Type.Skipped);
        mFirebaseRef.child(TABLE_NAME).push().setValue(action);
    }

    @NonNull
    private static Action createAction(String userId, Type type) {
        return new Action(
                    (String) FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    userId,
                    type);
    }

    public static void getMatches(final ActionDataCallbacks callback){

        final DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query query = mFirebaseRef.child(TABLE_NAME).orderByChild("type").equalTo(Type.Matched.getLabel());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> ids = new ArrayList<String>();
                for (DataSnapshot match : dataSnapshot.getChildren()){
                    if (!match.child("byUser").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        ids.add((String) match.child("byUser").getValue()); //then add the id of the byUser
                    }
                }
                if (callback != null) {
                    callback.onFetchedMatches(ids);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface ActionDataCallbacks {
        public void onFetchedMatches(List<String> matchIds);
    }
}
