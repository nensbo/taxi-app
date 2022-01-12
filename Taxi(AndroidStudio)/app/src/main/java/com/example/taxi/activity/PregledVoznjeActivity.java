package com.example.taxi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Voznja;
import com.example.taxi.dialog.LoadDialog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PregledVoznjeActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    ArrayList<Voznja> voznje=new ArrayList<>();
    String korisnik;

    int korisnikId,vozacId;
    Geocoder geocoder;
    List<Address> addresses;
    double latitude,longitude;
    TextView inputKrajnjaAdresa, inputPocetnaAdresa, inputStatus,inputVreme, labelPromena, labelOcena, labelObavestenje;
     LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;
    LinearLayout mainScrollView;
    RatingBar ratingBar;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private RatingBar ratingBarPopUp;
    EditText inputKomentar;
    Button buttonOcena;
    Button buttonOtkazi;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregled_voznje);
        mainScrollView = findViewById(R.id.mainScrollView);
        layoutInflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        apiInterface = ApiClient.getClient(ApiInterface.class);

        geocoder = new Geocoder(this, Locale.getDefault());

        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("vozacId")) {
                vozacId=bundle.getInt("vozacId");
                Toast.makeText(this,"Vozac id: "+ vozacId,Toast.LENGTH_LONG).show();
            }
            if (bundle.containsKey("korisnikId")) {
                korisnikId=bundle.getInt("korisnikId");
                Toast.makeText(this,"Korisnik id: "+ korisnikId,Toast.LENGTH_LONG).show();
            }}
        if(korisnikId!=0){
        findVoznja(korisnikId,"korisnik");}
        if(vozacId!=0){
            findVoznja(vozacId,"vozac");
        }

    }

private void kreiranjeAdrese(double latitude,double longitude){
    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
    } catch (IOException e) {
        e.printStackTrace();
    }

    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city = addresses.get(0).getLocality();
    String state = addresses.get(0).getAdminArea();
    String country = addresses.get(0).getCountryName();
    String postalCode = addresses.get(0).getPostalCode();
    String knownName = addresses.get(0).getFeatureName();
}
    private void findVoznja(int id,String tip) {
        if (id != 0) {
            Call<ArrayList<Voznja>> callLanguage = apiInterface.getVoznja(id,tip);

            final Dialog dialog = LoadDialog.loadDialog(PregledVoznjeActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<ArrayList<Voznja>>() {
                @Override
                public void onResponse(Call<ArrayList<Voznja>> call, Response<ArrayList<Voznja>> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {

                        voznje = response.body();
                        Toast.makeText(PregledVoznjeActivity.this,"Broj voznji "+voznje.size(),Toast.LENGTH_LONG).show();


                        for (int count = 0; voznje.size() > count; count++) {
                             relativeLayout = (RelativeLayout)
                                    layoutInflater.inflate(R.layout.my_view, null);
                            inputPocetnaAdresa=(TextView) relativeLayout.findViewById(R.id.inputPocetnaAdresa);
                            inputKrajnjaAdresa=(TextView) relativeLayout.findViewById(R.id.inputKrajnjaAdresa);
                            inputStatus=(TextView) relativeLayout.findViewById(R.id.inputStatus);
                            inputVreme=(TextView) relativeLayout.findViewById(R.id.inputVreme);
                            final int finalCount = count;
                                kreiranjeAdrese(voznje.get(count).getVoznjaDetalji().getPocetnaLokacijaLat(),voznje.get(count).getVoznjaDetalji().getPocetnaLokacijaLon());
                            inputPocetnaAdresa.setText(addresses.get(0).getAddressLine(0));
                            inputPocetnaAdresa.setFocusable(false);
                            kreiranjeAdrese(voznje.get(count).getVoznjaDetalji().getKrajnjaLokacijaLat(),voznje.get(count).getVoznjaDetalji().getKrajnjaLokacijaLon());
                            inputKrajnjaAdresa.setText(addresses.get(0).getAddressLine(0));
                            inputKrajnjaAdresa.setFocusable(false);
                            String status=null;
                            if(voznje.get(count).getStatus().equals("cekanje") | voznje.get(count).getStatus().equals(null))status="Čeka se preuzimanje vožnje!";
                        if(voznje.get(count).getStatus().equals("aktivna")) status="Vožnja je u toku!";
                            if(voznje.get(count).getStatus().equals("prihvacena")) status="Vozač je na putu do početne lokacije!";
                            if(voznje.get(count).getStatus().equals("zavrsena"))status="Vožnja je završena";
                        /*    ((TextView) relativeLayout.findViewById(R.id.inputKrajnjaAdresa)).setText(addresses.get(0).getAddressLine(0)+ ", "+addresses.get(0).getLocality());
                             ((TextView) relativeLayout.findViewById(R.id.inputVreme)).setText(voznje.get(count).getVoznjaDetalji().getVremeRezervacije());
                            ((TextView) relativeLayout.findViewById(R.id.inputStatus)).setText(status); */
                           inputVreme.setText(voznje.get(count).getVoznjaDetalji().getVremeRezervacije());
                            inputVreme.setFocusable(false);
                            inputStatus.setText(status);
                            inputStatus.setFocusable(false);
                            Toast.makeText(PregledVoznjeActivity.this,voznje.get(count).getStatus(),Toast.LENGTH_LONG).show();

                            if ((voznje.get(count)).getStatus().equals("zavrsena") ) {
                                labelOcena=relativeLayout.findViewById(R.id.labelOcena);
                                labelOcena.setVisibility(View.VISIBLE);
                                ratingBar=relativeLayout.findViewById(R.id.ratingBar);
                                ratingBar.setVisibility(View.VISIBLE);
                                ratingBar.setRating((voznje.get(count)).getOcena());
                                ratingBar.setFocusable(false);
                                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                        ratingBar.setRating((int)ratingBarPopUp.getRating());
                                    }
                                });
                                if(korisnikId!=0) {
                                    labelPromena = relativeLayout.findViewById(R.id.labelPromena);
                                    labelPromena.setVisibility(View.VISIBLE);
                                    final int finalCount1 = count;
                                    labelPromena.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ocenjivanjeVoznje((voznje.get(finalCount1)).getVoznjaId());
                                        }
                                    });
                                }}else{labelObavestenje=relativeLayout.findViewById(R.id.labelObavestenje);
                                    labelObavestenje.setVisibility(View.VISIBLE);
                            }
                            mainScrollView.addView(relativeLayout);
                        }

                    } else {
                        korisnik += "\n" + "Korisnik još uvek nema vožnji";

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Voznja>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(PregledVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    }public void ocenjivanjeVoznje(final int voznjaId){
        alertDialogBuilder=new AlertDialog.Builder(this);
        final View ocenjivanjeProzor=getLayoutInflater().inflate(R.layout.activity_ocena,null);
        ratingBarPopUp=(RatingBar)ocenjivanjeProzor.findViewById(R.id.ratingBar);
        inputKomentar=(EditText)ocenjivanjeProzor.findViewById(R.id.inputKomentar);
        buttonOcena=(Button)ocenjivanjeProzor.findViewById(R.id.buttonOcena);
        buttonOtkazi=(Button)ocenjivanjeProzor.findViewById(R.id.buttonOtkazi);
        alertDialogBuilder.setView(ocenjivanjeProzor);
        alertDialog=alertDialogBuilder.create();
        alertDialog.show();

        buttonOcena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingBar.setRating((int)ratingBarPopUp.getRating());
                azuriranjeVoznje(voznjaId);
                alertDialog.dismiss();
            }
        });

        buttonOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void azuriranjeVoznje(int voznjaId){
            if (voznjaId != 0) {
                Call<Void> callLanguage = apiInterface.updateVoznja(voznjaId,(int)ratingBarPopUp.getRating(),inputKomentar.getText().toString());
                final Dialog dialog = LoadDialog.loadDialog(PregledVoznjeActivity.this);
                dialog.show();
                callLanguage.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {

                            Toast.makeText(PregledVoznjeActivity.this, "Uspesno azuriranja voznja", Toast.LENGTH_SHORT).show();

                            // startIntent();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(PregledVoznjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing())
                            dialog.dismiss();

                    }
                });
            }}



    }


