package com.cm.pikachua;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cm.entities.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    final int RequestPermissionCode = 1;
    private Intent CropIntent;
    private Uri personPhoto;
    private String personID;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();
            personID = acct.getId();

            ImageView imageViewAndroid = findViewById(R.id.image);
            Picasso.with(this).load(personPhoto).into(imageViewAndroid);
        }

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        // app_title change listener
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    user = postSnapshot.getValue(User.class);
                    if(user.getId().equals(personID)){
                        updateUser(getWindow().getDecorView().getRootView(),getApplicationContext(),user);
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
        mFirebaseDatabase.addListenerForSingleValueEvent(postListener);

        /*FloatingActionButton button_change = findViewById(R.id.button_change);
        button_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 0);
            }
        });

        FloatingActionButton button_camera = findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.CAMERA);
                if(permissionCheck == PackageManager.PERMISSION_DENIED)
                    RequestRuntimePermission();

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "Pictures/PikachUA/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg"));
                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(camera, 1);
            }
        });*/

    }

    /*private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this, android.Manifest.permission.CAMERA))
            Toast.makeText(this,"CAMERA permission allows us to access CAMERA app",Toast.LENGTH_SHORT).show();
        else
        {
            ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{android.Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }*/

    public String getDecimal (int number) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbol = format.getDecimalFormatSymbols();
        symbol.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbol);

        return format.format(number);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK){
            if (data != null){
                uri = data.getData();
                CropImage();
            }
        }

        else if (requestCode == 1){
            CropImage();
        }

        else if (requestCode == 2){
            imageViewAndroid.setImageURI(uri2);
        }
    }

    private void CropImage() {

        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");
            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",200);
            CropIntent.putExtra("outputY",200);
            CropIntent.putExtra("aspectX",1);
            CropIntent.putExtra("aspectY",1);
            CropIntent.putExtra("return-data",true);
            uri2 = Uri.fromFile(new File(getExternalCacheDir(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg"));
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
            startActivityForResult(CropIntent,2);
        }
        catch (ActivityNotFoundException ex) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case RequestPermissionCode:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Permission Canceled",Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }*/

    public void updateUser(View view, Context context, User user)
    {
        TextView textViewAndroid1 = findViewById(R.id.name);
        textViewAndroid1.setText(getString(R.string.name_profile) + " " + user.getName());

        TextView textViewAndroid2 = (TextView) view.findViewById(R.id.startDate);
        textViewAndroid2.setText(getString(R.string.profile_member) + " " + user.getStartDate());

        TextView textViewAndroid3 = (TextView) view.findViewById(R.id.totalMonstersCaught);
        textViewAndroid3.setText(getString(R.string.monsters_total) + " " + user.getMonstersCaught());

        TextView textViewAndroid4 = (TextView) view.findViewById(R.id.totalEXP);
        textViewAndroid4.setText(getString(R.string.total_exp) + " " + user.getTotalXP());
    }
}
