package com.cm.pikachua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
public class GoogleLoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private DatabaseReference iFirebaseDatabase;
    private FirebaseDatabase iFirebaseInstance;

    ArrayList<User> list_users;

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    GoogleSignInClient mGoogleSignInClient;

    int next_idItem = 0;
    int next_idUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(final GoogleSignInAccount user) {
        //hideProgressDialog();
        if (user != null) {
            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), user.getId(), Toast.LENGTH_LONG).show();

            //getnextIDUser();
            //getnextIDItem();

            mFirebaseInstance = FirebaseDatabase.getInstance();
            mFirebaseDatabase = mFirebaseInstance.getReference("users");

            // app_title change listener
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User users = null;
                    list_users = new ArrayList<User>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        users = postSnapshot.getValue(User.class);
                        list_users.add(users);

                        if (users.getId().equals(user.getId())) {
                            Intent signInIntent = new Intent(getBaseContext(), MapsActivity.class);
                            signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(signInIntent);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            mFirebaseDatabase.addValueEventListener(postListener);

        } else {
            //mStatusTextView.setText(R.string.signed_out);
            //mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }

        /*if (list_users != null){
            if (!list_users.contains(user.getId())){*/
                /*User user_inst = new User(Integer.toString(next_idUser), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "0", "0");
                mFirebaseDatabase.child(String.valueOf(user.getId())).setValue(user_inst);
                createUser(user);
                Intent signInIntent = new Intent(getBaseContext(), MapsActivity.class);
                signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInIntent);*/
            /*}
        }*/
    }

    private void createUser(GoogleSignInAccount user){

        getBalls(user.getId());

    }

    private void getBalls(final String user_id){

        iFirebaseInstance = FirebaseDatabase.getInstance();
        iFirebaseDatabase = iFirebaseInstance.getReference("items_inst");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items");

        ValueEventListener postListener = new ValueEventListener() {
            public String amount;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemFirebase item = null;
                int i=0;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item = postSnapshot.getValue(ItemFirebase.class);
                    /*if (user_id + "_" + item.getId() == user_id + "_1"){
                        amount = "10";
                    }
                    else{
                        amount = "0";
                    }*/
                    ItemInst items_inst = new ItemInst(Integer.toString(next_idItem), item.getId(), user_id, item.getName(), item.getDescription(), "0", item.getImage());
                    iFirebaseDatabase.child(Integer.toString(next_idItem)).setValue(items_inst);
                    next_idItem++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        reference.addValueEventListener(postListener);
    }

    public void getnextIDItem(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemInst item_i = null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_i = postSnapshot.getValue(ItemInst.class);
                }
                next_idItem = Integer.parseInt(item_i.getId())+1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        reference.addValueEventListener(postListener);

    }

    public void getnextIDUser(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user_i = null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    user_i = postSnapshot.getValue(User.class);
                }
                next_idUser = Integer.parseInt(user_i.getId())+1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        reference.addValueEventListener(postListener);

    }
}