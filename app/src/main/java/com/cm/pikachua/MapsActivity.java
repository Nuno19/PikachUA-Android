package com.cm.pikachua;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.cm.pikachua.AR.UnityPlayerActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MapsActivity";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private double longitude;
    private double latitude;
    public static boolean buttons = false;
    private Marker currentPos;
    private static int[] markerPokemon = new int[3];
    private static Marker[] pokemonMarkers = new Marker[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    moveMap();
                }
            }
        };

        ImageView imageViewAndroid = (ImageView) findViewById(R.id.button_profile);
        imageViewAndroid.setImageResource(R.drawable.jv_7adtj);


        ImageButton but_profile = findViewById(R.id.button_profile);
        but_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(myIntent);
            }
        });

        FloatingActionButton but = findViewById(R.id.menu);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttons == false){
                    buttons = true;
                    //Toast.makeText(getApplicationContext(), "Mais", Toast.LENGTH_LONG).show();

                    ButtonsFragment newFragment = new ButtonsFragment();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.map_container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                    Log.d("T", "DailyFor");
                }
                else {
                    buttons = false;
                    //Toast.makeText(getApplicationContext(), "Menos", Toast.LENGTH_LONG).show();

                    getFragmentManager().popBackStack();
                }
            }
        });



        ImageView imageV2 = findViewById(R.id.imageV2);
        imageV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), RestockActivity.class);
                intent.putExtra("ID", Integer.toString(123));
                startActivity(intent);
            }
        });



        googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try {
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.skin_map));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        startLocation();
        Log.d(TAG, "Get_Location");
        // Add a marker in Sydney and move the camera
        //LatLng home = new LatLng(40.580764, -8.680022);
        LatLng ua = new LatLng(40.633115, -8.659362);
        //MarkerOptions marker = new MarkerOptions().position(ua);
        //marker.icon(BitmapDescriptorFactory.fromAsset("001.webp"));
        LatLng home_mewtwo = new LatLng(40.625745, -8.647508);

        if(markerPokemon[0] == 0) {
            pokemonMarkers[0] = mMap.addMarker(new MarkerOptions().position(home_mewtwo).icon(BitmapDescriptorFactory.fromResource(R.drawable.mewtwo)));
            pokemonMarkers[0].setTag(0);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getBaseContext(), LaunchUnity.class);
                    intent.putExtra("ID", 150);
                    intent.putExtra("markerID", (int) marker.getTag());
                    startActivity(intent);
                    return false;
                }
            });
        }
        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Curr"));
       // mMap.addMarker(new MarkerOptions().position(current_coords).title("HOME"));

        mMap.setMinZoomPreference(18);
        mMap.setMaxZoomPreference(21);

    }
    public static boolean setMarkerState(int markerID,int state) {
        Log.d("MAP","STATE: " + state);
        markerPokemon[markerID] = state;
        return true;
    }



    //Getting current location
    private void startLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        Log.e(TAG,"No Permission!");

        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100);
        mLocationRequest.setFastestInterval(50);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


         task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>(){

             @Override
             public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

             }


        });
         startLocationUpdates();

        Log.d("P","Lat: " + latitude + "Lon: " + longitude);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            Log.e(TAG,"No Permission!");
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG,"Permission Granted");
                } else {
                    Log.e(TAG,"Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG,"Permission Granted");
                } else {
                    Log.e(TAG,"Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        LatLng latLng = new LatLng(latitude, longitude);
    //    mMap.addMarker(new MarkerOptions()
      //          .position(latLng)
        //        .draggable(true)
          //      .title("Marker in India"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(12).build();
        if(currentPos != null){
            currentPos.remove();
        }

        currentPos = mMap.addMarker(new MarkerOptions().position(latLng));


        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.getUiSettings().setZoomControlsEnabled(true);


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




}
