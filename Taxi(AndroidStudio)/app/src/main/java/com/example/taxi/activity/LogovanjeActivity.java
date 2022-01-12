package com.example.taxi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozac;
import com.example.taxi.dialog.LoadDialog;

import java.net.HttpURLConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogovanjeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputKorisnickoIme, inputSifra;
    TextView labelRegistracija;
    Button buttonPotvrdi;
    ApiInterface apiInterface;
    private TextView labelKorisnik;
    int korisnikId;
    String email, sifra;
    Korisnik korisnik;
    Vozac vozac;
    RadioGroup tipKorisnika;
    RadioButton buttonTipKorisnika, radioButtonVozac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logovanje);

        buttonPotvrdi = (findViewById(R.id.buttonOcena));
        buttonPotvrdi.setOnClickListener(this);
        labelRegistracija=findViewById(R.id.labelRegistracija);
        labelRegistracija.setOnClickListener(this);
        inputKorisnickoIme =  findViewById(R.id.inputKorisnickoIme);
        inputSifra =  findViewById(R.id.inputSifra);
        labelKorisnik =  findViewById(R.id.labelKorisnik);
        tipKorisnika=findViewById(R.id.tipKorisnika);
        radioButtonVozac=findViewById(R.id.radioButtonVozac);
        apiInterface = ApiClient.getClient(ApiInterface.class);


    }


    private boolean getParametre() {
        Boolean popunjenaPolja=true;

        if (inputKorisnickoIme.getText().toString().isEmpty()) {
            inputKorisnickoIme.setError("Polje korisničko ime ne može biti prazno!");
            popunjenaPolja=false;
        }
        else{
            if(!Patterns.EMAIL_ADDRESS.matcher(inputKorisnickoIme.getText().toString()).matches()){
                inputKorisnickoIme.setError("Unesite korisničko ime u formi e-mail adrese!");
                popunjenaPolja=false;

            }
            else
            email=inputKorisnickoIme.getText().toString();}
        if (inputSifra.getText().toString().isEmpty()) {
            inputSifra.setError("Polje šifra ne može biti prazno!");
            popunjenaPolja=false;
        }
        else{
        sifra=inputSifra.getText().toString();}
        if(tipKorisnika.getCheckedRadioButtonId() == -1){
            radioButtonVozac.setError("Odaberite tip korisnika!");
            popunjenaPolja=false; }

                return popunjenaPolja;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonOcena:
               if(getParametre()){
                   checkButton();
               }
               else{ labelKorisnik.setText("Popunite sva polja");
               }
                break;
            case R.id.labelRegistracija:
                Intent intent = new Intent(LogovanjeActivity.this, RegistracijaActivity.class);
                startActivity(intent);
                break;

    }}

public void checkButton(){

      /*  int radioId=tipKorisnika.getCheckedRadioButtonId();
        buttonTipKorisnika=findViewById(radioId); */
       if( radioButtonVozac.isChecked()){
           findVozac();
       }
       else{ findKorisnik();}
    tipKorisnika.clearCheck();
    radioButtonVozac.setError(null);


}

    public void startIntent(Vozac vozac){
        Intent intent = new Intent(LogovanjeActivity.this, PronalazenjeVoznjeActivity.class);
        intent.putExtra("vozac",vozac);
        Toast.makeText(LogovanjeActivity.this, vozac.getEmail(),Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void startIntent(Korisnik korisnik){
        Intent intent = new Intent(LogovanjeActivity.this, ZakazivanjeVoznjeActivity.class);
        intent.putExtra("korisnik",korisnik);

        Toast.makeText(LogovanjeActivity.this, korisnik.getEmail(),Toast.LENGTH_LONG).show();
        startActivity(intent);
    }


    private void findKorisnik() {
            Call<Korisnik> callLanguage = apiInterface.getKorisnik(email, sifra);
            final Dialog dialog = LoadDialog.loadDialog(LogovanjeActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Korisnik>() {
                @Override
                public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {
                        korisnik=response.body();
                        labelKorisnik.setText("Korisnik" +korisnik.getKorisnikId()+" "+korisnik.getIme()+" "+korisnik.getPrezime());
                        startIntent(korisnik);
                    } else {
                        labelKorisnik.setText(getString(R.string.noKorisnik));
                    }

                }

                @Override
                public void onFailure(Call<Korisnik> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(LogovanjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    private void findVozac() {
        Call<Vozac> callLanguage = apiInterface.getVozac(email, sifra);
        final Dialog dialog = LoadDialog.loadDialog(LogovanjeActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<Vozac>() {
            @Override
            public void onResponse(Call<Vozac> call, Response<Vozac> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {
                    vozac=response.body();
                    Toast.makeText(LogovanjeActivity.this,vozac.toString() , Toast.LENGTH_SHORT).show();

                    startIntent(vozac);
                    labelKorisnik.setText(R.string.addKorisnik+response.body().toString());
                } else {
                    labelKorisnik.setText(getString(R.string.noKorisnik));
                }

            }

            @Override
            public void onFailure(Call<Vozac> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LogovanjeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });
    }
}