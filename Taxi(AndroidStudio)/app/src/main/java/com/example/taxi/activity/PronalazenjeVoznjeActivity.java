package com.example.taxi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taxi.DataConverter;
import com.example.taxi.FetchUrl;
import com.example.taxi.R;
import com.example.taxi.TaskLoadedCallback;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozac;
import com.example.taxi.data.Voznja;
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
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PronalazenjeVoznjeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener, TaskLoadedCallback {
    ApiInterface apiInterface;
    ArrayList<Voznja> voznje=new ArrayList<>();
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    LayoutInflater layoutInflater;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    Vozac vozac;
    Korisnik korisnik;
    TextView imePrezimeNaslov, korisnickoIme;
    ImageView slika;
    HashMap<Double,Integer> distance;
    private static final String TAG = PronalazenjeVoznjeActivity.class.getSimpleName();
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
    public static double distanca,trajanje;
    int brojac=0;
double minDistanca=0;
    private Marker startMarker;
    private Marker endMarker;
    private Polyline currentPolyline;
    Button buttonPronadjiVoznju;
    ArrayList<Double> arrayDistance;
    TextView labelPronalazenjeVoznje;
    int voznjaPronadjena=0, intVoznjaPreuzeta =0;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    EditText inputInformacijeVoznja;
    Button buttonPrihvati,buttonOtkazi,buttonPreuzetiVoznju,buttonOdjaviVoznju, buttonZavrsenaVoznja;
    Geocoder geocoder;
    List<Address> addresses;
    int voznjaId;
    LinearLayout linearLayoutVoznja;
    String voznjaPodaci=null;
    Voznja voznjaPrihvacena=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_pronalazenje_voznje);
        apiInterface = ApiClient.getClient(ApiInterface.class);
        Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        drawerLayout = findViewById(R.id.drawerLayout);
//        (findViewById(R.id.imageMenu)).setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        LayoutInflater layoutInflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         distance=new HashMap<>();
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.navigacija_naslov, null);
        View headerView = navigationView.getHeaderView(0);
        //   frameLayout=(FrameLayout) layoutInflater.inflate(R.layout.fragment_kontejner,null);
        places1Client = Places.createClient(this);
        labelPronalazenjeVoznje=findViewById(R.id.labelPronalazenjeVoznje);

        vozac = (Vozac) getIntent().getSerializableExtra("vozac");
        if (vozac != null) {
            Toast.makeText(this, vozac.getIme() + vozac.getPrezime(), Toast.LENGTH_LONG).show();
        }
        toolbar = findViewById(R.id.toolbar);
        buttonPronadjiVoznju=findViewById(R.id.buttonPronadjiVoznju);
        buttonPronadjiVoznju.setOnClickListener(this);
        buttonPreuzetiVoznju=findViewById(R.id.buttonPreuzetaVoznja);
        buttonPreuzetiVoznju.setOnClickListener(this);
        buttonOdjaviVoznju=findViewById(R.id.buttonOdjaviVoznju);
        buttonOdjaviVoznju.setOnClickListener(this);
        buttonZavrsenaVoznja=findViewById(R.id.buttonZavrsenaVoznja);
        buttonZavrsenaVoznja.setOnClickListener(this);
        linearLayoutVoznja=findViewById(R.id.linearLayoutVoznja);
        imePrezimeNaslov = (TextView) headerView.findViewById(R.id.labelImeIPrezime);
        korisnickoIme = (TextView) headerView.findViewById(R.id.labelMenuKorisnickoIme);
        slika = headerView.findViewById(R.id.imageRounded);
        slika.setClipToOutline(true);
        if (vozac != null) {
            korisnickoIme.setText(vozac.getEmail());
            imePrezimeNaslov.setText(vozac.getIme() + " " + vozac.getPrezime());
            slika.setImageBitmap(DataConverter.convertString2Image(vozac.getSlika()));
        }
    /*  NavController navController= Navigation.findNavController(this,R.id.navigationView);
           NavigationUI.setupWithNavController(navigationView,navController);
        setSupportActionBar(toolbar); */
        geocoder = new Geocoder(this, Locale.getDefault());

  /*   actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_bar_nav_open,R.string.app_bar_nav_close);
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
       actionBarDrawerToggle.syncState(); */
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuProfil:
                intent = new Intent(this, ProfilActivity.class);
                intent.putExtra("vozac", vozac);
                startActivityForResult(intent, 4325);
                break;
            case R.id.menuNotifikacije:
                intent = new Intent(this, NotifikacijeActivity.class);
                startActivity(intent);
                break;
            case R.id.menuONama:
                intent = new Intent(this, ONamaActivity.class);
                startActivity(intent);
                break;
            case R.id.menuVoznje:
                intent = new Intent(this, PregledVoznjeActivity.class);
                intent.putExtra("vozacId", vozac.getVozacId());
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

        if (requestCode == 4325) {
            if (resultCode == Activity.RESULT_OK) {
                vozac = (Vozac) data.getSerializableExtra("vozac");
                if (vozac != null) {
                    korisnickoIme.setText(vozac.getEmail());
                    imePrezimeNaslov.setText(vozac.getIme() + " " + vozac.getPrezime());
                    slika.setImageBitmap(DataConverter.convertString2Image(vozac.getSlika()));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private static void odjava(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

    private void trenutnaLokacijaUredjaja() {
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
                                LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        latLng, DEFAULT_ZOOM));
                                startMarker = mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .draggable(true));

                            }
                        } else {
                            Log.d(TAG, "Trenutna lokacija nije definisana. Koristi se difoltna.");
                            Log.e(TAG, "Greška: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
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
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        //  Toast.makeText(this, startMarker.getPosition().latitude+" "+startMarker.getPosition().longitude, Toast.LENGTH_SHORT).show();

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        trenutnaLokacijaUredjaja();
    }

    private void findVoznja() {
            Call<ArrayList<Voznja>> callLanguage = apiInterface.getVoznja();

            final Dialog dialog = LoadDialog.loadDialog(PronalazenjeVoznjeActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<ArrayList<Voznja>>() {
                @Override
                public void onResponse(Call<ArrayList<Voznja>> call, Response<ArrayList<Voznja>> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {

                        voznje = response.body();
                    }

                    if(voznje.isEmpty()){
                    Toast.makeText(PronalazenjeVoznjeActivity.this,"Nema zahteva za vožnju, pokušajte kasnije!", Toast.LENGTH_LONG).show();}


            }

                @Override
                public void onFailure(Call<ArrayList<Voznja>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(PronalazenjeVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                });

        }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPronadjiVoznju:
            findVoznja();
                for (int i = 0; i < voznje.size(); i++) {
                LatLng pocetnaAdresa = new LatLng(voznje.get(i).getVoznjaDetalji().getPocetnaLokacijaLat(), voznje.get(i).getVoznjaDetalji().getPocetnaLokacijaLon());
                new FetchUrl(PronalazenjeVoznjeActivity.this).execute(getUrl(startMarker.getPosition(), pocetnaAdresa, "driving"), "driving");
            }
            break;
            case R.id.buttonPreuzetaVoznja:
                azuriranjeVoznje(voznjaId,2);
            //    deletePolyline();
                intVoznjaPreuzeta=1;
                new FetchUrl(PronalazenjeVoznjeActivity.this).execute(getUrl((new LatLng(voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLon())), (new LatLng(voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLon())), "driving"), "driving");

                //notifikacija o vozilu koje dolazi
                //crtanje nove putanje
                break;

            case R.id.buttonOdjaviVoznju:

                otkazivanjeVoznje(this);

                //notifikacija o ponovnom stavljanju na cekanje
                break;
            case R.id.buttonZavrsenaVoznja:
                azuriranjeVoznje(voznjaId,3);
                voznjaPrihvacena=null;
                intVoznjaPreuzeta =0;
                voznjaPronadjena=0;
                linearLayoutVoznja.setVisibility(View.GONE);
                Toast.makeText(PronalazenjeVoznjeActivity.this,"Vožnja je uspešno završena!",Toast.LENGTH_LONG).show();
                deletePolyline();
                if(startMarker!=null) {startMarker.remove(); trenutnaLokacijaUredjaja();}
                if(endMarker!=null) endMarker.remove();
                //notifikacija o oceni i komentaru
                break;
        }
    }

    private void otkazivanjeVoznje(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Otkazivanje vožnje");
        builder.setMessage("Da li ste sigurni da želite da otkažete vožnju?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                azuriranjeVoznje(voznjaId,4);
                deletePolyline();
               if(startMarker!=null) {startMarker.remove(); trenutnaLokacijaUredjaja();}
               if(endMarker!=null) endMarker.remove();
                voznjaPrihvacena=null;
                intVoznjaPreuzeta =0;
                voznjaPronadjena=0;
                linearLayoutVoznja.setVisibility(View.GONE);
                Toast.makeText(PronalazenjeVoznjeActivity.this,"Vožnja je otkazana!",Toast.LENGTH_LONG).show();
                dialog.dismiss();


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
    public void onTaskDone(Object... values) {
        if (voznjaPronadjena == 0) {
            if (minDistanca != 0) {
                {
                    if (minDistanca > distanca) minDistanca = distanca;
                }
            } else minDistanca = distanca;
            distance.put(distanca, brojac);
            brojac++;
            if (brojac == voznje.size()) {
                int position=0;
                brojac = 0;
                position = distance.get(minDistanca);
                Voznja voznja = voznje.get(position);
                voznjaId = voznja.getVoznjaId();
                pronalazenjeVoznje(voznja);
                labelPronalazenjeVoznje.setText("Početna lokacija: " + voznja.getVoznjaDetalji().getPocetnaLokacijaLat() + voznja.getVoznjaDetalji().getPocetnaLokacijaLon() + " Distanca: " + minDistanca + "km" + " Vreme: " + trajanje + " minuta");
                minDistanca=0;
                voznje.clear();
            }
            Toast.makeText(PronalazenjeVoznjeActivity.this, "Distanca " + distanca+ voznjaPronadjena, Toast.LENGTH_LONG).show();
        } else {

            if (currentPolyline != null)
                currentPolyline.remove();
                currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

            if (intVoznjaPreuzeta !=0){
                preuzimanjeVoznje();
            voznjaPronadjena = 0;
                Toast.makeText(PronalazenjeVoznjeActivity.this, "Pokrenuto preuzimanje voznje " + distanca, Toast.LENGTH_LONG).show();
            }

        }
    }
    public void deletePolyline(){
        if (currentPolyline != null)
            currentPolyline.remove();
    }

    private void kreiranjeAdrese(double latitude,double longitude) {
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void preuzimanjeVoznje(){
        kreiranjeAdrese(voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLon());
        String pocetnaAdresa=addresses.get(0).getAddressLine(0);
        kreiranjeAdrese(voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLon());
        String krajnjaAdresa=addresses.get(0).getAddressLine(0);
        labelPronalazenjeVoznje.setText("Početna lokacija: "+ pocetnaAdresa+ "\n" +
                "Krajnja lokacija: "+ krajnjaAdresa+"\n" +
                "Razdaljina do krajnje lokacije: "+distanca+" km "+ "\n"+
                "Procenjeno vreme za koje se stiže na lokaciju: "+trajanje+" minuta");
        if(startMarker!=null){startMarker.remove();}
        if(endMarker!=null){endMarker.remove();}
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLon()), DEFAULT_ZOOM));

        startMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getPocetnaLokacijaLon()))
                .draggable(true));
        endMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLat(),voznjaPrihvacena.getVoznjaDetalji().getKrajnjaLokacijaLon()))
                .draggable(true));

    }
    public void pronalazenjeVoznje(final Voznja voznja){
        if(voznja!=null){
        kreiranjeAdrese(voznja.getVoznjaDetalji().getPocetnaLokacijaLat(),voznja.getVoznjaDetalji().getPocetnaLokacijaLon());
        String pocetnaAdresa=addresses.get(0).getAddressLine(0);
        kreiranjeAdrese(voznja.getVoznjaDetalji().getKrajnjaLokacijaLat(),voznja.getVoznjaDetalji().getKrajnjaLokacijaLon());
        String krajnjaAdresa=addresses.get(0).getAddressLine(0);
        alertDialogBuilder=new AlertDialog.Builder(this);
        final View preuzimanjeVoznje=getLayoutInflater().inflate(R.layout.activity_voznja,null);
        inputInformacijeVoznja=(EditText)preuzimanjeVoznje.findViewById(R.id.inputInformacijeVoznja);
        voznjaPodaci="Početna lokacija: "+ pocetnaAdresa+ "\n" +
                "Krajnja lokacija: "+ krajnjaAdresa+"\n" +
                "Procenjeno trajanje vožnje: "+ voznja.getVoznjaDetalji().getTrajanjeVoznje()+" minuta"+"\n" +
                "Razdaljina do početne lokacije: "+minDistanca+" km "+ "\n"+
                "Procenjeno vreme za koje se stiže na lokaciju: "+trajanje+" minuta" ;
        inputInformacijeVoznja.setText(voznjaPodaci);
        inputInformacijeVoznja.setFocusable(false);
     /*   inputInformacijeVoznja.setText("Početna lokacija: "+ pocetnaAdresa+"\n"+
                                       " Krajnja lokacija: "+ krajnjaAdresa+"\n"+
                                       "  Procenjeno trajanje vožnje: "+ voznja.getVoznjaDetalji().getTrajanjeVoznje()+"minuta"+"\n"+
                                       " Distanca do početne lokacije:  "+minDistanca+" km "+ "\n"+
                                        "Procenjeno vreme za koje se stiže na lokaciju:  "+trajanje+" minuta" ); */

        buttonPrihvati=(Button)preuzimanjeVoznje.findViewById(R.id.buttonPrihvati);
        buttonOtkazi=(Button)preuzimanjeVoznje.findViewById(R.id.buttonOtkaziVoznju);
        alertDialogBuilder.setView(preuzimanjeVoznje);
        alertDialog=alertDialogBuilder.create();
        alertDialog.show();

        buttonPrihvati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng izabranaVoznja=new LatLng(voznja.getVoznjaDetalji().getPocetnaLokacijaLat(),voznja.getVoznjaDetalji().getPocetnaLokacijaLon());
                voznjaPronadjena=1;
                new FetchUrl(PronalazenjeVoznjeActivity.this).execute(getUrl(startMarker.getPosition(), izabranaVoznja, "driving"), "driving");
                voznjaPrihvacena=voznja;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        startMarker.getPosition(), DEFAULT_ZOOM));
                endMarker = mMap.addMarker(new MarkerOptions()
                        .position(izabranaVoznja)
                        .draggable(true));

Toast.makeText(PronalazenjeVoznjeActivity.this,"Voznja je prihvacena",Toast.LENGTH_LONG).show();
                azuriranjeVoznje(voznja.getVoznjaId(),1);
                linearLayoutVoznja.setVisibility(View.VISIBLE);
                labelPronalazenjeVoznje.setText(voznjaPodaci);
                labelPronalazenjeVoznje.setFocusable(false);
                alertDialog.dismiss();
            }
        });

        buttonOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }}


    public void azuriranjeVoznje(int voznjaId,int status){
        Call<Void> callLanguage = apiInterface.updateVoznjaStatus(voznjaId, vozac.getVozacId(),status,1);

        final Dialog dialog = LoadDialog.loadDialog(PronalazenjeVoznjeActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.code() == HttpURLConnection.HTTP_OK && response.message() != null) {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PronalazenjeVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

    }
    }



