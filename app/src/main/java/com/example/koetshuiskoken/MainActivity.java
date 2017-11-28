package com.example.koetshuiskoken;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "***" + MainActivity.class.getSimpleName();

    private static final String ANONYMOUS = "anonymous";

    private static final int RC_SIGN_IN = 1;

    private String mUsername;

    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    protected static Context context;

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate()");

        context = this.getApplicationContext();

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
        Log.d(LOG_TAG, "onResume()");
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    //************************************************************************
    //*                 onPause
    //************************************************************************
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        // TODO mMessageAdapter.clear();
        detachDatabaseReadListener();
    }
    //************************************************************************
    //*                 onCreateOptionsMenu
    //************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    //************************************************************************
    //*                 onOptionsItemSelected
    //************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //************************************************************************
    //*                 onSignedInInitialize
    //************************************************************************
    private void onSignedInInitialize(String username) {
        Log.d(LOG_TAG, "onSignedInInitialize()");
        mUsername = username;
        detachDatabaseReadListener();
        // Create a new adapter that takes an empty listMyDayOfWeek as input.
        MyDayOfWeekAdapter mAdapter =
            new MyDayOfWeekAdapter(this, new ArrayList<MyDayOfWeek>());
        // Add a array of {@link MyDayOfWeek}s to the adapter's
        // data set. This will trigger the ListView to update.
        mAdapter.addAll(QueryUtils.fetchMyDayOfWeekData());
        // show the week list
        showWeekList();
    }
    //************************************************************************
    //*                 showWeekList
    //************************************************************************
    private void showWeekList() {
        // Create a new adapter that takes an empty listMyDayOfWeek as input.
        MyDayOfWeekAdapter mAdapter =
                new MyDayOfWeekAdapter(this, new ArrayList<MyDayOfWeek>());
        // Add a array of {@link MyDayOfWeek}s to the adapter's
        // data set. This will trigger the ListView to update.
        mAdapter.addAll(QueryUtils.fetchMyDayOfWeekData());

        // take care of the right height of the RelativeLayout
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        ListView mListView = (ListView) findViewById(R.id.list);
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height;
        mListView.setLayoutParams(params);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface.
        // Find a reference to the {@link ListView} in the layout.
        ListView dowListView = (ListView) findViewById(R.id.list);
        // set the adapter
        dowListView.setAdapter(mAdapter);
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
