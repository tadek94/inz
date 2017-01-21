package com.example.tadziu.forrunners1;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //TextView sprawdzenie = (TextView) findViewById(R.id.sprawdzenie);

    public double distance = 0;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LatLng firstLocation;
    Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    double promienR = 6371;
    TextView odleglosc; // do widoku
    TextView cal;
    //Button zapisz = (Button) findViewById(R.id.zapiszTrase);
    Ekran ekran = new Ekran();
    BaseManager baseManager;
    long czasStrt;
    long czasStop;



  //  LatLng firstLatLon = null;
    private ArrayList<LatLng> points; //lista z punktami
    Polyline line; //to sie bedzie rysowało po punktach
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    LocationManager locationManager;
    private View view;

    Kalorie kalorie = new Kalorie();

    static final int requestcode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        points = new ArrayList<LatLng>();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//        //locationManager.requestLocationUpdates(bestProvider, 0, 0, locListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 second
                .setFastestInterval(1 * 1000); // 1 second

        points = new ArrayList<LatLng>();
        odleglosc = (TextView) findViewById(R.id.odleglosc);
        cal = (TextView) findViewById(R.id.kalorie);
        baseManager = new BaseManager(this);
        czasStrt = System.currentTimeMillis();



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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }




    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }// dodane connect

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(200);
        mLocationRequest.setFastestInterval(200);

        mLocationRequest.setSmallestDisplacement(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }

    }


    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        points.add(latLng);
//        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("Aktualna pozycja");
//        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15)); // 15 optymalne
        if (points.size() > 1) {
            int dlugoscListy = points.size();
            LatLng start = points.get(dlugoscListy-2);
            LatLng stop = points.get(dlugoscListy-1);
            distance =distance + getDystance(start,stop);
            odleglosc.setText(Double.toString(distance).substring(0,5)+ " km");
            cal.setText(Double.toString(kalorie.spaloneKalorieBieganie(distance,70)).substring(0,5) + "kcal");
        }
        draw();
    }
//do obliczania długosci
    public double getDystance(LatLng szerDluPocz, LatLng szerDluKoncowa)
    {

        double szerPocz = szerDluPocz.latitude;//start
        double dluPocz = szerDluPocz.longitude;//strat

        double szerKon = szerDluKoncowa.latitude;//stop
        double dluKon = szerDluKoncowa.longitude;//stop

        double roznicaSzer = szerPocz - szerKon;
        double roznicaDlu = dluPocz - dluKon;


        //odleglosc.setText("szerokosc:" + Double.toString(roznicaSzer)+ " dlugosc:"+ Double.toString(roznicaDlu));

        double poziomo = (roznicaDlu*6.283184/360)*promienR*cos(szerPocz*6.283184/360);
        double pionowo = promienR*roznicaSzer*6.283184/360;

        double dystans = sqrt(poziomo*poziomo+pionowo*pionowo);

        return dystans;


    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void draw() {


        mMap.clear();  //czyscimy
        firstLocation = points.get(0); //dl i szer geograficzna
        MarkerOptions option = new MarkerOptions()
                .position(firstLocation)
                .title("START");
        mMap.addMarker(option);




        PolylineOptions options = new PolylineOptions().width(12).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
            //System.out.print("poz"+point);
        }


        line = mMap.addPolyline(options);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }


    Bitmap bitmapZdj;
    private static final int PERMS_REQUEST_CODE = 123;

    //zrobienie zdj trasy i zapis do bazy danych

    public void onClickZdj(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,requestcode );
        }
    }

    public void onActivityResult(int reqcode, int resultcode, Intent data)
    {
        if(reqcode == requestcode)
        {
            if(resultcode == RESULT_OK)
            {
                Bundle b = new Bundle();
                b = data.getExtras();
                bitmapZdj = (Bitmap) b.get("data");

            }
        }

    }
    public void onClickZapiszTrase(View view)
    {
        this.view = view;
        View layout1 = getWindow().getDecorView().findViewById(android.R.id.content);
        //final RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.mojlayout);

        czasStop = System.currentTimeMillis();
        long czasBiegania = czasStop-czasStrt;
        double godz = (czasBiegania/ (1000*60*60));
        if(layout1 != null) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //ekranBitmap = ekran.getScreenShot(layout1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapZdj.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            //zapis do bazy danych
            double szybkosc = distance/godz;
            baseManager.dodajTrase(date, distance, szybkosc, byteArray);

            //CaptureScreen();
            Toast toast = Toast.makeText(this, "Zapisano do bazy", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(this, "bład", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    private void CaptureScreen() {

            SnapshotReadyCallback callback = new SnapshotReadyCallback() {
                Bitmap bitmap=null;

                @Override
                public void onSnapshotReady(Bitmap snapshot) {
                    // TODO Auto-generated method stub
                    bitmap = snapshot;
                    try {
                        saveImage(bitmap);
                        Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                private void saveImage(Bitmap bitmap) throws IOException {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
                    File f = new File(getFilesDir(), "test.png");
                    //File f = new File(Environment.getExternalStorageDirectory() + File.separator + "test.png");
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();

                }
            };

            mMap.snapshot(callback);

    }

    //////////////////////////////////////////
    //zapis do pliku

//    private void saveScreenshot(Bitmap bm) {
//
//        ByteArrayOutputStream bao = null;
//        File file = null;
//
//        try {
//            //kompresja i zapis strumienia zewnetrznego
//
//            bao = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 90, bao);
//
//            //zapis obrazka na kartę SD
//            file = new File(Environment.getExternalStorageDirectory() + File.separator+"czlowiek_1.png");
//            file.createNewFile();
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bao.toByteArray());
//            fos.close();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//    }
//
//    private boolean hasPermission() {
//        int res = 0;
//
//        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//        for (String perms : permission) {
//
//            res = checkCallingOrSelfPermission(perms);
//            if (!(res == PackageManager.PERMISSION_GRANTED)) {
//
//                return false;
//            }
//
//        }
//
//        return true;
//
//    }
//
//
//    private void requestPermis() {
//        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            requestPermissions(permission, PERMS_REQUEST_CODE);
//        }
//
//    }

////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Maps Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
}
