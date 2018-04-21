package com.cm.pikachua;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.common.helpers.CameraPermissionHelper;
import com.cm.common.helpers.DisplayRotationHelper;
import com.cm.common.helpers.SnackbarHelper;
import com.cm.common.helpers.TapHelper;
import com.cm.common.rendering.BackgroundRenderer;
import com.cm.common.rendering.ObjectRenderer;
import com.cm.common.rendering.PlaneRenderer;
import com.cm.common.rendering.PointCloudRenderer;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Point;
import com.google.ar.core.PointCloud;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CatchActivity extends AppCompatActivity implements Renderer {

    private static final String TAG = CatchActivity.class.getSimpleName();

    // Rendering. The Renderers are created here, and initialized when the GL surface is created.
    private GLSurfaceView surfaceView;

    private boolean installRequested;

    private Session session;
    private final SnackbarHelper messageSnackbarHelper = new SnackbarHelper();
    private DisplayRotationHelper displayRotationHelper;
    private TapHelper tapHelper;

    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();
    private ObjectRenderer virtualObject = null;
    private final ObjectRenderer virtualObjectShadow = null;
    private final PlaneRenderer planeRenderer = new PlaneRenderer();
    private final PointCloudRenderer pointCloudRenderer = new PointCloudRenderer();

    // Temporary matrix allocated here to reduce number of allocations for each frame.
    private final float[] anchorMatrix = new float[16];

    // Anchors created from taps used for object placing.
    private final ArrayList<Anchor> anchors = new ArrayList<>();

    AlertDialog alertDialog1, alertDialog2;
    CharSequence[] values_ball = {" PokeBall (0) ", " Great Ball (1) ", " Ultra Ball (2)"};
    CharSequence[] values_berry = {"None (0) ", "Razz Berry (1) ", " Golden Razz Berry (2) "};
    int choice_ball = 0;
    int choice_berry = 0;
    Pokemon pokemonToCatch = null;
    String cp = "0";
    int next_id = 0;
    private String personID;
    private String pokemon_id;
    private String pokemon_name;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_catch );

        surfaceView = findViewById( R.id.surfaceview );
        displayRotationHelper = new DisplayRotationHelper(/*context=*/ this );

        // Set up tap listener.
        tapHelper = new TapHelper(/*context=*/ this );
        surfaceView.setOnTouchListener( tapHelper );

        // Set up renderer.
        surfaceView.setPreserveEGLContextOnPause( true );
        surfaceView.setEGLContextClientVersion( 2 );
        surfaceView.setEGLConfigChooser( 8, 8, 8, 8, 16, 0 ); // Alpha used for plane blending.
        surfaceView.setRenderer( this );
        surfaceView.setRenderMode( GLSurfaceView.RENDERMODE_CONTINUOUSLY );

        installRequested = false;

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
        if (acct != null) {
            personID = acct.getId();
        }

        pokemon_id = getIntent().getStringExtra( "ID" );
        //Toast.makeText(CatchActivity.this, "Pokémon: " + id, Toast.LENGTH_LONG).show();
        virtualObject = new ObjectRenderer( String.format( "%03d", Integer.parseInt(pokemon_id) ) + "/" + String.format( "%03d", Integer.parseInt(pokemon_id) ) + ".obj");
        //virtualObject = new ObjectRenderer( "007/007.obj");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "pokemons" );

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon mon = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mon = postSnapshot.getValue(Pokemon.class);
                    if(mon.getId().equals(pokemon_id)) {
                        setPokemon( getWindow().getDecorView().getRootView(), mon );
                        pokemon_name = mon.getName();
                        pokemonToCatch = mon;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException() );
                // ...
            }
        };
        reference.addValueEventListener( postListener );

        getnextID();

        //String s = pokemonToCatch.getName();

        FloatingActionButton button_back = findViewById( R.id.button_back );
        button_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        } );

        Button button_balls = findViewById( R.id.button_balls );
        button_balls.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Ball", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder( CatchActivity.this );
                builder1.setTitle( "Select Your Ball" );
                builder1.setSingleChoiceItems( values_ball, choice_ball, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "PokeBall", Toast.LENGTH_LONG).show();
                                choice_ball = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Great Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 1;
                                break;
                            case 2:
                                //Toast.makeText(CatchActivity.this, "Ultra Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 2;
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                } );
                alertDialog1 = builder1.create();
                alertDialog1.show();
            }
        } );

        Button button_berries = findViewById( R.id.button_berries );
        button_berries.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Berries", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder2 = new AlertDialog.Builder( CatchActivity.this );
                builder2.setTitle( "Select Your Berry" );
                builder2.setSingleChoiceItems( values_berry, choice_berry, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "None", Toast.LENGTH_LONG).show();
                                choice_berry = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 1;
                                break;
                            case 4:
                                //Toast.makeText(CatchActivity.this, "Golden Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 2;
                                break;
                        }
                        alertDialog2.dismiss();
                    }
                } );
                alertDialog2 = builder2.create();
                alertDialog2.show();
            }
        } );

        FloatingActionButton fab = findViewById( R.id.floatingActionButton );
        fab.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                int catchSimValue = catchSimulation( pokemonToCatch, choice_berry, choice_ball );

                if (catchSimValue == 1) {
                    Toast.makeText( CatchActivity.this,
                            "Got it!", Toast.LENGTH_LONG )
                            .show();


                    DatabaseReference database = FirebaseDatabase.getInstance().getReference( "pokemonsInst" );
                    PokemonInst pokemon_inst = new PokemonInst( personID + "_" + String.format( "%012d", next_id ), pokemonToCatch.getId(), personID, pokemonToCatch.getName(), cp, pokemonToCatch.getImage() );
                    database.child( personID + "_" + String.format( "%012d", next_id ) ).setValue( pokemon_inst );

                    onBackPressed();
                } else if (catchSimValue == 2) {
                    Toast.makeText( CatchActivity.this,
                            "Oh No! It escaped!", Toast.LENGTH_LONG )
                            .show();
                } else {
                    Toast.makeText( CatchActivity.this,
                            "Oh No! It ran away!", Toast.LENGTH_LONG )
                            .show();
                    onBackPressed();
                }
            }
        } );
    }

    public void setPokemon(View view, Pokemon pokemon) {
        TextView text = (TextView) view.findViewById( R.id.title1 );
        int random_cp = (int) (Math.random() * 2000 + 1);
        text.setText( pokemon.getName() + ": " + random_cp );
        cp = String.valueOf( random_cp );
    }


    public void getnextID() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "pokemonsInst" );
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PokemonInst pokemon = null;
                next_id = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    pokemon = postSnapshot.getValue( PokemonInst.class );
                    if (pokemon.getUser_id().equals( personID )) {
                        next_id = Integer.parseInt( pokemon.getId().split( "_" )[1] ) + 1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException() );
                // ...
            }
        };
        reference.addValueEventListener( postListener );

    }


    public int catchSimulation(Pokemon pokemon, int berry, int ball) {
        int catchRate = (int) (Double.parseDouble( pokemon.getCatchRate() ) * 100);
        int fleeRate = (int) (Double.parseDouble( pokemon.getFleeRate() ) * 100);
        switch (berry) {
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.5;
                break;
            default:
                break;
        }

        switch (ball) {
            case 0:
                break;
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.0;
                break;
        }

        int random_value_catch = (int) (Math.random() * 100 + 1);

        if (random_value_catch < catchRate)
            return 1;

        int random_value_flee = (int) (Math.random() * 100 + 1);

        if (random_value_flee < fleeRate)
            return 3;

        return 2;

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (session == null) {
            Exception exception = null;
            String message = null;
            try {
                switch (ArCoreApk.getInstance().requestInstall( this, !installRequested )) {
                    case INSTALL_REQUESTED:
                        installRequested = true;
                        return;
                    case INSTALLED:
                        break;
                }

                // ARCore requires camera permissions to operate. If we did not yet obtain runtime
                // permission on Android M and above, now is a good time to ask the user for it.
                if (!CameraPermissionHelper.hasCameraPermission( this )) {
                    CameraPermissionHelper.requestCameraPermission( this );
                    return;
                }

                // Create the session.
                session = new Session(/* context= */ this );

            } catch (UnavailableArcoreNotInstalledException
                    | UnavailableUserDeclinedInstallationException e) {
                message = "Please install ARCore";
                exception = e;
            } catch (UnavailableApkTooOldException e) {
                message = "Please update ARCore";
                exception = e;
            } catch (UnavailableSdkTooOldException e) {
                message = "Please update this app";
                exception = e;
            } catch (UnavailableDeviceNotCompatibleException e) {
                message = "This device does not support AR";
                exception = e;
            } catch (Exception e) {
                message = "Failed to create AR session";
                exception = e;
            }

            if (message != null) {
                messageSnackbarHelper.showError( this, message );
                Log.e( TAG, "Exception creating session", exception );
                return;
            }
        }

        // Note that order matters - see the note in onPause(), the reverse applies here.
        try {
            session.resume();
        } catch (CameraNotAvailableException e) {
            // In some cases (such as another camera app launching) the camera may be given to
            // a different app instead. Handle this properly by showing a message and recreate the
            // session at the next iteration.
            messageSnackbarHelper.showError( this, "Camera not available. Please restart the app." );
            session = null;
            return;
        }

        surfaceView.onResume();
        displayRotationHelper.onResume();

        messageSnackbarHelper.showMessage( this, "Searching for surfaces..." );
    }

    @Override
    public void onPause() {
        super.onPause();
        if (session != null) {
            // Note that the order matters - GLSurfaceView is paused first so that it does not try
            // to query the session. If Session is paused before GLSurfaceView, GLSurfaceView may
            // still call session.update() and get a SessionPausedException.
            displayRotationHelper.onPause();
            surfaceView.onPause();
            session.pause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasCameraPermission( this )) {
            Toast.makeText( this, "Camera permission is needed to run this application", Toast.LENGTH_LONG )
                    .show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale( this )) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings( this );
            }
            finish();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Standard Android full-screen functionality.
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor( 0.1f, 0.1f, 0.1f, 1.0f );

        // Prepare the rendering objects. This involves reading shaders, so may throw an IOException.
        try {
            // Create the texture and pass it to ARCore session to be filled during update().
            backgroundRenderer.createOnGlThread(/*context=*/ this );
            session.setCameraTextureName(backgroundRenderer.getTextureId());
            planeRenderer.createOnGlThread(/*context=*/ this, "trigrid.png" );
            pointCloudRenderer.createOnGlThread(/*context=*/ this );

            virtualObject.createOnGlThread( this );

            //virtualObject.createOnGlThread(/*context=*/ this, "models/andy.obj", "models/andy.png" );
            virtualObject.setMaterialProperties( 1.0f, 0.0f, 0.0f, 1.0f );

            //virtualObjectShadow.createOnGlThread(
                    /*context=*/ //this, "models/andy_shadow.obj", "models/andy_shadow.png" );
            //virtualObjectShadow.setBlendMode( ObjectRenderer.BlendMode.Shadow );
            //virtualObjectShadow.setMaterialProperties( 1.0f, 0.0f, 0.0f, 1.0f );

        } catch (IOException e) {
            Log.e( TAG, "Failed to read an asset file", e );
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        displayRotationHelper.onSurfaceChanged( width, height );
        GLES20.glViewport( 0, 0, width, height );
        session.setDisplayGeometry(0,width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear screen to notify driver it should not load any pixels from previous frame.
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT );

        try {
            // Obtain the current frame from ARSession. When the configuration is set to
            // UpdateMode.BLOCKING (it is by default), this will throttle the rendering to the
            // camera framerate.
            Frame frame = session.update();
            Camera camera = frame.getCamera();

            // Handle taps. Handling only one tap per frame, as taps are usually low frequency
            // compared to frame rate.
            MotionEvent tap = tapHelper.poll();
            if (tap != null && camera.getTrackingState() == TrackingState.TRACKING) {
                for (HitResult hit : frame.hitTest( tap )) {
                    // Check if any plane was hit, and if it was hit inside the plane polygon
                    Trackable trackable = hit.getTrackable();
                    // Creates an anchor if a plane or an oriented point was hit.
                    if ((trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon( hit.getHitPose() ))
                            || (trackable instanceof Point
                            && ((Point) trackable).getOrientationMode()
                            == Point.OrientationMode.ESTIMATED_SURFACE_NORMAL)) {
                        // Hits are sorted by depth. Consider only closest hit on a plane or oriented point.
                        // Cap the number of objects created. This avoids overloading both the
                        // rendering system and ARCore.
                        if (anchors.size() >= 20) {
                            anchors.get( 0 ).detach();
                            anchors.remove( 0 );
                        }
                        // Adding an Anchor tells ARCore that it should track this position in
                        // space. This anchor is created on the Plane to place the 3D model
                        // in the correct position relative both to the world and to the plane.
                        anchors.add( hit.createAnchor() );
                        break;
                    }
                }
            }

            // Draw background.
            backgroundRenderer.draw( frame );

            // If not tracking, don't draw 3d objects.
            if (camera.getTrackingState() == TrackingState.PAUSED) {
                return;
            }

            // Get projection matrix.
            float[] projmtx = new float[16];
            camera.getProjectionMatrix( projmtx, 0, 0.1f, 100.0f );

            // Get camera matrix and draw.
            float[] viewmtx = new float[16];
            camera.getViewMatrix( viewmtx, 0 );

            // Compute lighting from average intensity of the image.
            // The first three components are color scaling factors.
            // The last one is the average pixel intensity in gamma space.
            final float lightIntensity = frame.getLightEstimate().getPixelIntensity();

            // Visualize tracked points.
            PointCloud pointCloud = frame.acquirePointCloud();
            pointCloudRenderer.update( pointCloud );
            pointCloudRenderer.draw( viewmtx, projmtx );

            // Application is responsible for releasing the point cloud resources after
            // using it.
            pointCloud.release();

            // Check if we detected at least one plane. If so, hide the loading message.
            if (messageSnackbarHelper.isShowing()) {
                for (Plane plane : session.getAllTrackables( Plane.class )) {
                    if (plane.getType() == com.google.ar.core.Plane.Type.HORIZONTAL_UPWARD_FACING
                            && plane.getTrackingState() == TrackingState.TRACKING) {
                        messageSnackbarHelper.hide( this );
                        break;
                    }
                }
            }

            // Visualize planes.
            planeRenderer.drawPlanes(
                    session.getAllTrackables( Plane.class ), camera.getDisplayOrientedPose(), projmtx );

            // Visualize anchors created by touch.
            float scaleFactor = 0.005f;

            if (pokemon_id.equals("0")){
                scaleFactor = 0.0025f;
            }

            else if (pokemon_id.equals("5")){
                scaleFactor = 0.0001f;
            }

            else if (pokemon_id.equals("9")){
                scaleFactor = 0.0025f;
            }

            for (Anchor anchor : anchors) {
                if (anchor.getTrackingState() != TrackingState.TRACKING) {
                    continue;
                }
                // Get the current pose of an Anchor in world space. The Anchor pose is updated
                // during calls to session.update() as ARCore refines its estimate of the world.
                anchor.getPose().toMatrix( anchorMatrix, 0 );

                // Update and draw the model and its shadow.
                virtualObject.updateModelMatrix( anchorMatrix, scaleFactor );
                //virtualObjectShadow.updateModelMatrix( anchorMatrix, scaleFactor );
                virtualObject.draw(viewmtx, projmtx, lightIntensity);
                //virtualObjectShadow.draw(viewmtx, projmtx, colorCorrectionRgba);
            }

        } catch (Throwable t) {
            // Avoid crashing the application due to unhandled exceptions.
            Log.e( TAG, "Exception on the OpenGL thread", t );
        }
    }
}