package com.example.koetshuiskoken;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String ANONYMOUS = "anonymous";

    private static final int RC_SIGN_IN = 1;

    private String mUsername;

    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // do authorization
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                        AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(
                                    Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }
    //************************************************************************
    //*                 onResume
    //************************************************************************
    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    //************************************************************************
    //*                 onSignedInInitialize
    //************************************************************************
    private void onSignedInInitialize(String username) {
        Log.d(LOG_TAG, "onSignedInInitialize()");
        mUsername = username;
        detachDatabaseReadListener();
    }
    //************************************************************************
    //*                 detachDatabaseReadListener
    //************************************************************************
    private void detachDatabaseReadListener() {
        Log.d(LOG_TAG, "detachDatabaseReadListener()");
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
    //************************************************************************
    //*                 onSignedOutCleanUp
    //************************************************************************
    private void onSignedOutCleanUp() {
        Log.d(LOG_TAG, "onSignedOutCleanUp()");
        mUsername = ANONYMOUS;
        // TODO mMessageAdapter.clear();
        detachDatabaseReadListener();
    }
}
