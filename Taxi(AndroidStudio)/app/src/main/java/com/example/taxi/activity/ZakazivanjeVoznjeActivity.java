package com.example.taxi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxi.FetchUrl;
import com.example.taxi.PlacesAutoCompleteAdapter;
import com.example.taxi.R;
import com.example.taxi.TaskLoadedCallback;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Cena;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Voznja;
import com.example.taxi.data.VoznjaDetalji;
import com.example.taxi.data.VoznjaDodaci;
import com.example.taxi.dialog.LoadDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZakazivanjeVoznjeActivity extends AppCompatActivity implements View.OnClickListener, PlacesAutoCompleteAdapter.ClickListener, OnMapReadyCallback, TaskLoadedCallback,NavigationView.OnNavigationItemSelectedListener /*implements OnMapReadyCallback */ {
 /*   private static final String TAG = "ZakazivanjeActivity";
    //google map object
    private GoogleMap mMap;

    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment; */
 private static final String TAG = ZakazivanjeVoznjeActivity.class.getSimpleName();
 public static double distanca;
 public static int trajanje;
    private Marker startMarker;
    private Marker endMarker;
    Polyline polylines;
    Korisnik korisnik;
LatLng latLngStart,latLngEnd;
    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private PlacesClient places1Client;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LatLng defaultLocation = new LatLng(44.787197, 20.457273);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    TextView labelImeIPrezime, labelMenuKorisnickoIme, labelTrajanje,labelDistanca,labelCena;
    ImageView imageRounded;
 DrawerLayout drawerLayout;
 LinearLayout layoutDodaci, layoutJezici;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    EditText inputStartPlaceSearch,inputEndPlaceSearch;
    Button buttonProcenaVoznje;
    private Polyline currentPolyline;
String evalue;
int cenaStart,cenaPoKm;
Button buttonNaruci;
CheckBox checkBoxDodaci,checkBoxSediste,checkBoxSedisteBebe,checkBoxJezik, checkBoxEkoloskoVozilo,checkBoxEnglish,checkBoxFrench,checkBoxSpanish,checkBoxRussian;
    ApiInterface apiInterface;
Toolbar toolbar;
ActionBarDrawerToggle actionBarDrawerToggle;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_zakazivanje_voznje);
        Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        drawerLayout=findViewById(R.id.drawerLayout);
//        (findViewById(R.id.imageMenu)).setOnClickListener(this);
        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
                LayoutInflater layoutInflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout relativeLayout =(RelativeLayout) layoutInflater.inflate(R.layout.navigacija_naslov, null);
toolbar=findViewById(R.id.toolbar);

  //      NavigationUI.setupWithNavController(navigationView,navController);
        korisnik= (Korisnik) getIntent().getSerializableExtra("korisnik");
        View headerView = navigationView.getHeaderView(0);
        labelImeIPrezime = (TextView) headerView.findViewById(R.id.labelImeIPrezime);
        labelImeIPrezime.setText(korisnik.getIme()+" "+korisnik.getPrezime());
        labelMenuKorisnickoIme=headerView.findViewById(R.id.labelMenuKorisnickoIme);
        labelMenuKorisnickoIme.setText(korisnik.getEmail());
        ImageView korisnikSlika=headerView.findViewById(R.id.imageRounded);
        korisnikSlika.setVisibility(View.GONE);
        buttonProcenaVoznje=findViewById(R.id.buttonProcenaVoznje);
        buttonProcenaVoznje.setOnClickListener(this);
        apiInterface = ApiClient.getClient(ApiInterface.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        recyclerView = (RecyclerView) findViewById(R.id.places_recycler_view);
        inputStartPlaceSearch=(EditText) findViewById(R.id.inputStartPlaceSearch);
        inputStartPlaceSearch.addTextChangedListener(filterTextWatcher);
        inputEndPlaceSearch=findViewById(R.id.inputEndPlaceSearch);
        inputEndPlaceSearch.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        places1Client=Places.createClient(this);
        checkBoxDodaci=findViewById(R.id.checkBoxDodaci);
        checkBoxEkoloskoVozilo=findViewById(R.id.checkBoxEkoloskoVozilo);
        checkBoxJezik=findViewById(R.id.checkBoxStraniJezik);
        checkBoxJezik.setOnClickListener(this);
        checkBoxSediste=findViewById(R.id.checkBoxDodatnaSedista);
        checkBoxSedisteBebe=findViewById(R.id.checkBoxSedisteZaBebe);
        checkBoxEnglish=findViewById(R.id.checkBoxEnglish);
        checkBoxSpanish=findViewById(R.id.checkBoxSpanish);
        checkBoxRussian=findViewById(R.id.checkBoxRussian);
        checkBoxFrench=findViewById(R.id.checkBoxFrench);
        checkBoxDodaci.setOnClickListener(this);
        layoutDodaci=findViewById(R.id.layoutDodaci);
        layoutJezici=findViewById(R.id.layoutJezici);
        layoutJezici.setOnClickListener(this);
        setSupportActionBar(toolbar);
        labelTrajanje=findViewById(R.id.labelTrajanje);
        labelDistanca=findViewById(R.id.labelDistanca);
        labelCena=findViewById(R.id.labelCena);
        cenaVoznje();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    /*    actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_bar_nav_open,R.string.app_bar_nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState(); */
     /*   fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.navHostFragment,new ProfilFragment());
        fragmentTransaction.commit(); */
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        buttonNaruci =findViewById(R.id.buttonNaruci);
        buttonNaruci.setOnClickListener(this);
        inputStartPlaceSearch.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                evalue = "1";
                return false;
            }
            });

        inputEndPlaceSearch.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                evalue = "2";
                return false;
            }
        });



}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuProfil:
                intent=new Intent(this, ProfilActivity.class);
                intent.putExtra("korisnik",korisnik);
                startActivityForResult(intent,4323);
                break;
            case R.id.menuNotifikacije:
                intent=new Intent(this, NotifikacijeActivity.class);
                startActivity(intent);
                break;
            case R.id.menuONama:
                intent=new Intent(this, ONamaActivity.class);
                startActivity(intent);
                break;
            case R.id.menuVoznje:
                intent=new Intent(this, PregledVoznjeActivity.class);
                intent.putExtra("korisnikId",korisnik.getKorisnikId());
                startActivity(intent);
                break;
            case R.id.menuLogOut:
                odjava(this);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4323) {
            if(resultCode == Activity.RESULT_OK){
                korisnik= (Korisnik) data.getSerializableExtra("korisnik");
                if(korisnik!=null){
                    labelMenuKorisnickoIme.setText(korisnik.getEmail());
                    labelImeIPrezime.setText(korisnik.getIme()+" "+korisnik.getPrezime());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

private void odjava(final Activity activity){
    AlertDialog.Builder builder=new AlertDialog.Builder(activity);
    builder.setTitle("Odjava");
    builder.setMessage("Da li ste sigurni da želite da se odjavite?");
    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            activity.finishAffinity();
            System.exit(0);
        }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    builder.show();
}




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
       /*     case R.id.imageMenu:
                drawerLayout.openDrawer(GravityCompat.START);
                break; */

            case R.id.buttonProcenaVoznje:

                new FetchUrl(ZakazivanjeVoznjeActivity.this).execute(getUrl(startMarker.getPosition(), endMarker.getPosition(), "driving"), "driving");
                Toast.makeText(this, "Trajanje: "+trajanje+"Distanca: "+distanca, Toast.LENGTH_SHORT).show();
            buttonNaruci.setVisibility(View.VISIBLE);
            labelDistanca.setVisibility(View.VISIBLE);
            labelDistanca.setText("Procenjena dužina vožnje: "+distanca+" km");
                labelTrajanje.setVisibility(View.VISIBLE);
                labelTrajanje.setText("Procenjeno trajanje vožnje: "+trajanje+" minuta");
                labelCena.setVisibility(View.VISIBLE);
                double cena=distanca*cenaPoKm+cenaStart;
                labelCena.setText("Procenjena cena vožnje: "+cena+" RSD");
                break;
            case R.id.buttonNaruci:
                kreirajVoznju();
                break;
            case R.id.checkBoxDodaci:
                if(checkBoxDodaci.isChecked())
                layoutDodaci.setVisibility(View.VISIBLE);
                else layoutDodaci.setVisibility(View.GONE);
                break;
            case R.id.checkBoxStraniJezik:
                if(checkBoxJezik.isChecked())
                    layoutJezici.setVisibility(View.VISIBLE);
                else layoutJezici.setVisibility(View.INVISIBLE);

        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {

            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (recyclerView.getVisibility() == View.GONE) {recyclerView.setVisibility(View.VISIBLE);}
            } else {
                if (recyclerView.getVisibility() == View.VISIBLE) {recyclerView.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }
@Override
    public void click(Place place) {
        Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
   if(evalue.equals("1")){
       inputStartPlaceSearch.setText(place.getAddress());
       if(startMarker!=null){startMarker.remove();}
       deletePolyline();
       startMarker= mMap.addMarker(new MarkerOptions()
               .position(place.getLatLng())
               .draggable(true));
       mMap.moveCamera(CameraUpdateFactory
               .newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));

   }
   else if(evalue.equals("2")){
        inputEndPlaceSearch.setText(place.getAddress());
        if(endMarker!=null){endMarker.remove();}
        deletePolyline();
        endMarker= mMap.addMarker(new MarkerOptions()
                .position(place.getLatLng())
                .draggable(true));
       mMap.moveCamera(CameraUpdateFactory
               .newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
    }
    recyclerView.setVisibility(View.GONE);



  // updateLocation(place.getLatLng());


}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap=googleMap;
        LatLng lattLng=new LatLng( 45.267136,19.833549);
        endMarker= mMap.addMarker(new MarkerOptions()
                .position(lattLng)
                .draggable(true));
        Toast.makeText(this, endMarker.getPosition().latitude+" "+endMarker.getPosition().longitude, Toast.LENGTH_SHORT).show();

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                LatLng latLng=new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        latLng, DEFAULT_ZOOM));
                               startMarker= mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .draggable(true));

                            }
                        } else {
                            Log.d(TAG, "Trenutna lokacija nije pronadjena. Koristi se difoltna.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onTaskDone(Object... values) {

            if (currentPolyline != null)
                currentPolyline.remove();
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }
    public void deletePolyline(){
        if (currentPolyline != null)
            currentPolyline.remove();
    }


    public void kreirajVoznju(){
        int i1=0,i2=0,i3=0,i4=0;
        String straniJezik=null;
        String engleski="English";
        String spanski="Spanish";
        String francuski="French";
        String ruski="Russian";
        Timestamp vremeRezervacije=new Timestamp(System.currentTimeMillis());
        if(checkBoxDodaci.isChecked()){
            if(checkBoxEkoloskoVozilo.isChecked())i1=1;
                if(checkBoxSedisteBebe.isChecked())i2=1;
                if(checkBoxJezik.isChecked())straniJezik="engleski";
                if(checkBoxSediste.isChecked())i3=7;
        }
        if(checkBoxJezik.isChecked()){
            if(checkBoxEnglish.isChecked()) straniJezik=engleski;
            if(checkBoxSpanish.isChecked()) straniJezik=spanski;
            if(checkBoxFrench.isChecked()) straniJezik=francuski;
            if(checkBoxRussian.isChecked()) straniJezik=ruski;
        }
        Toast.makeText(ZakazivanjeVoznjeActivity.this, "Trajanje voznje"+ trajanje, Toast.LENGTH_SHORT).show();

        VoznjaDodaci voznjaDodaci=new VoznjaDodaci(i1,i2,i3,straniJezik);
        VoznjaDetalji voznjaDetalji=new VoznjaDetalji(vremeRezervacije.toString(),trajanje,startMarker.getPosition().latitude,startMarker.getPosition().longitude,endMarker.getPosition().latitude,endMarker.getPosition().longitude);
        Toast.makeText(ZakazivanjeVoznjeActivity.this, voznjaDetalji.getPocetnaLokacijaLat()+" "+voznjaDetalji.getPocetnaLokacijaLon(), Toast.LENGTH_SHORT).show();
        Voznja voznja=new Voznja(korisnik,"cekanje",voznjaDetalji,voznjaDodaci);
    makeVoznja(voznja);



    }



    private void makeVoznja(Voznja voznja) {
        if (voznja != null) {
            Call<Integer> callLanguage = apiInterface.makeVoznja(voznja);
            final Dialog dialog = LoadDialog.loadDialog(ZakazivanjeVoznjeActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {

                        Toast.makeText(ZakazivanjeVoznjeActivity.this, "Uspesno dodata voznja", Toast.LENGTH_SHORT).show();
                        // startIntent();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ZakazivanjeVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }}
public void cenaVoznje(){

    Call<Cena> callLanguage = apiInterface.getVoznjaCena("cena");
    final Dialog dialog = LoadDialog.loadDialog(ZakazivanjeVoznjeActivity.this);
    dialog.show();
    callLanguage.enqueue(new Callback<Cena>() {
        @Override
        public void onResponse(Call<Cena> call, Response<Cena> response) {
            if (dialog.isShowing())
                dialog.dismiss();
            if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {
cenaStart=response.body().getCenaStart();
cenaPoKm=response.body().getCenaPoKm();

            }
        }

        @Override
        public void onFailure(Call<Cena> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(ZakazivanjeVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
            if (dialog.isShowing())
                dialog.dismiss();

        }
    });

    }








}




