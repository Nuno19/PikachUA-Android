package com.cm.pikachua;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageViewAndroid;
    private boolean isTwoPane = false;

    File file;
    Uri uri;
    Intent CamIntent,GalIntent,CropIntent;
    final int RequestPermissionCode=1;
    DisplayMetrics displayMetrics;
    int width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        determinePaneLayout();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });

        String[] user = new String[] {"Raimas96", "2017-03-22", getDecimal(34378989), getDecimal(2100000000)};
        int userInt = R.drawable.jv_7adtj;

        TextView textViewAndroid1 = (TextView) findViewById(R.id.username);
        textViewAndroid1.setText("Username: " + user[0]);

        TextView textViewAndroid2 = (TextView) findViewById(R.id.startDate);
        textViewAndroid2.setText("Member since: " + user[1]);

        TextView textViewAndroid3 = (TextView) findViewById(R.id.totalMonstersCaught);
        textViewAndroid3.setText("Total monsters caught: " + user[2]);

        TextView textViewAndroid4 = (TextView) findViewById(R.id.totalEXP);
        textViewAndroid4.setText("Total EXP earned: " + user[3]);

        imageViewAndroid = (ImageView) findViewById(R.id.image);
        imageViewAndroid.setImageResource(userInt);

        imageViewAndroid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "És feio, esperavas o quê?", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton button_change = findViewById(R.id.button_change);
        button_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),2);
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

                CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                file = new File(Environment.getExternalStorageDirectory(),
                        "PikachUA/" + timeStamp + ".jpg");
                uri = Uri.fromFile(file);
                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                CamIntent.putExtra("return-data",true);
                startActivityForResult(CamIntent,0);
            }
        });

    }

    private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this, android.Manifest.permission.CAMERA))
            Toast.makeText(this,"CAMERA permission allows us to access CAMERA app",Toast.LENGTH_SHORT).show();
        else
        {
            ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{android.Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        // If there is a second pane for details
        if (fragmentItemDetail != null) {
            isTwoPane = true;
        }
    }

    public String getDecimal (int number) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbol = format.getDecimalFormatSymbols();
        symbol.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbol);

        return format.format(number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK)
            CropImage();

        else if(requestCode == 2 && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                uri = data.getData();
                CropImage();
            }
        }
        else if (requestCode == 1 && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageViewAndroid.setImageBitmap(bitmap);
            }
        }
    }

    private void CropImage() {

        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");

            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",200);
            CropIntent.putExtra("outputY",200);
            CropIntent.putExtra("aspectX",4);
            CropIntent.putExtra("aspectY",4);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent,1);
        }
        catch (ActivityNotFoundException ex)
        {

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
    }
}
