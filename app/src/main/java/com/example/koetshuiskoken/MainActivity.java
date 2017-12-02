package com.example.koetshuiskoken;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + MainActivity.class.getSimpleName();
    public static final String FIREBASE_URL = "https://koetshuiskoken2017.firebaseio.com/";
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    // adapter for the list day of week
    private DayOfWeekAdapter mAdapter;

    protected static Context context;

    private ListView dowListView;

    private String mUsername;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Firebase mRef;

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        // necessary for transferring Firebase to DinnerActivity
        Firebase.setAndroidContext(this);
        // create firebase reference
        mRef = new Firebase(FIREBASE_URL);

        mUsername = ANONYMOUS;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseReference =
            mFirebaseDatabase.getReference().child("dinner");

        // start log in procedure
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // user is signed out
                    onSignedOutCleanup();
                    startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                        RC_SIGN_IN);
                }
            }
        };

        // Find a reference to the {@link ListView} in the layout.
        dowListView = (ListView) findViewById(R.id.list);
        // Create a new adapter that takes an empty listDayOfWeek as input.
        mAdapter = new DayOfWeekAdapter(this, new ArrayList<MyDayOfWeek>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface.
        dowListView.setAdapter(mAdapter);
        // Set an item click listener on the ListView, which starts an intent
        // to open a form with information about the selected day of week.
        dowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i(LOG_TAG, "DayOfWeek[" + position + "].onItemClick()");
                // Find the current DayOfWeek that was clicked on
                MyDayOfWeek currentDayOfWeek = mAdapter.getItem(position);

                // start new activity with necessary data
                Intent intentDinnerActivity = new Intent(MainActivity.this,
                        DinnerActivity.class);
                setExtras(intentDinnerActivity,
                        currentDayOfWeek.getColor(),
                        currentDayOfWeek.getNameOfDay(),
                        currentDayOfWeek.getDate(),
                        FIREBASE_URL);
                startActivity(intentDinnerActivity);
            }
        });

        // Add a array of {@link DayOfWeek}s to the adapter's
        // data set. This will trigger the ListView to update.
        mAdapter.addAll(QueryUtils.fetchDayOfWeekData());

    }
    //************************************************************************
    //*                 setExtras
    //************************************************************************
    /**
     * Bring the GUI characteristics to the activity.
     */
    private void setExtras(Intent intent, int iColor, String strNameDay,
        String strDateDay, String firebaseUrl) {
        Log.i(LOG_TAG, "setExtras()");
        intent.putExtra("color", iColor);
        intent.putExtra("name_day", strNameDay);
        intent.putExtra("date_day", strDateDay);
        intent.putExtra("FIREBASE_URL", firebaseUrl);
    }

    //************************************************************************
    //*                 onResume
    //************************************************************************
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    //************************************************************************
    //*                 onPause
    //************************************************************************
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    //************************************************************************
    //*                 onCreateOptionsMenu
    //************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, "onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //************************************************************************
    //*                 onOptionsItemSelected
    //************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                Log.i(LOG_TAG, "Sign out from Firebase");
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
        mUsername = username;
        Log.i(LOG_TAG, "onSignedInInitialize() " + mUsername);
        attachDatabaseReadListener();
        //********************************************************************
        // test database
//        List<String> listEating = new ArrayList<>();
//        listEating.add("Anne");
//        listEating.add("Mark");
//        JsonObject jsonObject = new JsonObject("SAT", "12-02-2017",
//                "Jim", "Soup", (ArrayList<String>)listEating);
//        mMessagesDatabaseReference.push().setValue(jsonObject);
        //********************************************************************
    }

    //************************************************************************
    //*                 onSignedOutCleanup
    //************************************************************************
    private void onSignedOutCleanup() {
        Log.i(LOG_TAG, "onSignedOutCleanup()");
        mUsername = ANONYMOUS;
        detachDatabaseReadListener();
    }

    //************************************************************************
    //*                 attachDatabaseReadListener
    //************************************************************************
    private void attachDatabaseReadListener() {
        Log.i(LOG_TAG, "attachDatabaseReadListener()");
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.i(LOG_TAG, "onChildAdded()");
                }
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.i(LOG_TAG, "onChildChanged()");
                }
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.i(LOG_TAG, "onChildRemoved()");
                }
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    Log.i(LOG_TAG, "onChildMoved()");
                }
                public void onCancelled(DatabaseError databaseError) {
                    Log.i(LOG_TAG, "onCancelled()");
                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    //************************************************************************
    //*                 detachDatabaseReadListener
    //************************************************************************
    private void detachDatabaseReadListener() {
        Log.i(LOG_TAG, "detachDatabaseReadListener()");
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
}
