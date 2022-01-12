package com.example.taxi.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taxi.DataConverter;
import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozac;
import com.example.taxi.data.Vozilo;
import com.example.taxi.dialog.LoadDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistracijaActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG =RegistracijaActivity.class.getSimpleName() ;
    ApiInterface apiInterface;
    EditText inputIme, inputPrezime, inputEmail, inputPhone, inputAdresa, inputSifra;
    Button buttonRegistracija,buttonSlika;
    Korisnik korisnik;
    Vozac vozac;
    Vozilo vozilo;
    int korisnikId,vozacId;
    CheckBox checkBoxVozac;
    LinearLayout layoutVozac;
    String ime = null,prezime=null,telefon=null,email=null,adresa=null,sifra=null;
   EditText inputGodineStarosti,inputGodineIskustva, inputStraniJezik,inputModelVozila, inputBrojSedista, inputRegistarskiBroj, inputSlika;
 Switch switchVozilo,switchSediste;
 String straniJezik, modelVozila, registarskiBroj, slika;
 int ekoloskoVozilo,sediste,godineStarosti, brojSedista, godineIskustva;
Bitmap bitmap;
byte[] arraySlika;
String stringSlika,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        apiInterface = ApiClient.getClient(ApiInterface.class);
        inputIme =  findViewById(R.id.inputIme);
        inputPrezime =  findViewById(R.id.inputPrezime);
        inputEmail =  findViewById(R.id.inputEmail);
        inputPhone =  findViewById(R.id.inputPhone);
        inputAdresa=findViewById(R.id.inputAdresa);
        inputSifra=findViewById(R.id.inputSifra);
        inputGodineStarosti=findViewById(R.id.inputGodineStarosti);
        inputGodineIskustva=findViewById(R.id.inputGodineIskustva);
        inputStraniJezik=findViewById(R.id.inputStraniJezik);
        inputModelVozila=findViewById(R.id.inputModelVozila);
        inputBrojSedista=findViewById(R.id.inputBrojSedista);
        inputRegistarskiBroj=findViewById(R.id.inputRegistarskiBroj);
        inputSlika=findViewById(R.id.inputSlika);
        switchVozilo=findViewById(R.id.switchVozilo);
        switchSediste=findViewById(R.id.switchSediste);
        checkBoxVozac=findViewById(R.id.checkBoxVozac);
        buttonSlika=findViewById(R.id.buttonSlika);
        buttonSlika.setOnClickListener(this);
        checkBoxVozac.setOnClickListener(this);
        (findViewById(R.id.buttonRegistracija)).setOnClickListener(this);
        layoutVozac=findViewById(R.id.layoutVozac);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.e(TAG,"Greska pri generisanju tokena");
                    return;}
                token=task.getResult();
                Toast.makeText(RegistracijaActivity.this,"Token: " +token,Toast.LENGTH_LONG).show();
                Log.d(TAG,"Token: "+ token);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Token nije uspesno generisan"+e.getLocalizedMessage());
            }
        });

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.buttonSlika:
                proveraDozvola();
            case R.id.checkBoxVozac:
                if(checkBoxVozac.isChecked()){
                    layoutVozac.setVisibility(View.VISIBLE);
                }
                else{
                    layoutVozac.setVisibility(View.GONE);
                }
                break;
            case R.id.buttonRegistracija:
                    proveraParametara();
                if(checkBoxVozac.isChecked()){
                    proveraParametaraVozac();
                addVozac();}
                else
                addKorisnik();
                break;
        }
    }
    public void proveraDozvola(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions,4567);

            }
            else{
                pickImageFromGallery();
            }
        }
        else{
                pickImageFromGallery();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==4567 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                pickImageFromGallery();
        }
        else{
            Toast.makeText(RegistracijaActivity.this,"Dozvola je odbijena...!", Toast.LENGTH_SHORT).show();

        }
    }

    public void proveraParametara(){
        if (inputIme.getText().toString().isEmpty()) {
            inputIme.setError("Polje ime ne može biti prazno!");}
        else{
         ime=inputIme.getText().toString();}
        if (inputPrezime.getText().toString().isEmpty()) {
                inputPrezime.setError("Polje prezime ne može biti prazno!");}
        else{ prezime=inputPrezime.getText().toString();}
         if (inputEmail.getText().toString().isEmpty()) {
                    inputEmail.setError("Polje E-mail ne može biti prazno!");}
         else if (!isEmailValid(inputEmail.getText().toString())) {
            inputEmail.setError("E-mail je nevažeći. Unesite ponovo! ");}
         else{
             email=inputEmail.getText().toString();
         }

         if (inputSifra.getText().toString().isEmpty()) {
            inputSifra.setError("Polje šifra ne može biti prazno!");}
       else if (inputSifra.getText().length() < 6) {
            inputSifra.setError("Šifra mora imati više od 6 karaktera");}
        else {
        sifra=inputSifra.getText().toString();}
        if (inputPhone.getText().toString().isEmpty()){
            inputPhone.setError("Polje broj telefona ne može biti prazno");
        }
        else{
            telefon=inputPhone.getText().toString();}
            if (inputAdresa.getText().toString().isEmpty()) {
                inputAdresa.setError("Polje adresa ne može biti prazno!");
        }
            else{
                 adresa=inputAdresa.getText().toString();}

        korisnik=new Korisnik(ime,prezime,email,telefon,sifra,adresa,token);
        Toast.makeText(RegistracijaActivity.this, "Korisnik Ime"+ korisnik.getIme()+korisnik.getPrezime()+korisnik.getAdresa()+korisnik.getEmail()+korisnik.getSifra()+korisnik.getTelefon(), Toast.LENGTH_SHORT).show();


    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void proveraParametaraVozac(){
        if (inputSlika.getText().toString().isEmpty()) {
            inputSlika.setError("Polje slika ne može biti prazno!");}
        else{
            slika=inputSlika.getText().toString();


        }
        if (inputGodineStarosti.getText().toString().isEmpty()) {
            inputGodineStarosti.setError("Polje godine starosti ne može biti prazno!");}
        else{
            godineStarosti=Integer.parseInt(inputGodineStarosti.getText().toString());}

        if (inputGodineIskustva.getText().toString().isEmpty()) {
            inputGodineIskustva.setError("Polje godine iskustva ne može biti prazno!");}
        else{
            godineIskustva=Integer.parseInt(inputGodineIskustva.getText().toString());}

        if (!inputStraniJezik.getText().toString().isEmpty()) {
            straniJezik=inputStraniJezik.getText().toString();}
        if (inputModelVozila.getText().toString().isEmpty()) {
            inputModelVozila.setError("Polje model i tip vozila ne može biti prazno!");}
        else{
            modelVozila=inputModelVozila.getText().toString();}

        if (inputBrojSedista.getText().toString().isEmpty()) {
            inputBrojSedista.setError("Polje broj sedišta ne može biti prazno!");}
        else{
            brojSedista=Integer.parseInt(inputBrojSedista.getText().toString());}

        if (inputRegistarskiBroj.getText().toString().isEmpty()) {
            inputRegistarskiBroj.setError("Polje registarski broj ne može biti prazno!");}
        else{
            registarskiBroj=inputRegistarskiBroj.getText().toString();}

        if (switchVozilo.isChecked())
            ekoloskoVozilo=1;
        else ekoloskoVozilo=0;

        if (switchSediste.isChecked())
            sediste=1;
        else sediste=0;


        vozilo=new Vozilo(modelVozila,registarskiBroj,ekoloskoVozilo,sediste,brojSedista);
        vozac=new Vozac(vozilo,ime,prezime,email,telefon,sifra,adresa,stringSlika,godineStarosti,godineIskustva,straniJezik,"neaktivan",token);


    }
    private void pickImageFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,435);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 435) {
            inputSlika.setText(data.getData().toString());
            try {
                bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(RegistracijaActivity.this, bitmap.toString(), Toast.LENGTH_SHORT).show();
            if(bitmap!=null){
                arraySlika= DataConverter.convertImage2ByteArray(bitmap);
                stringSlika = Base64.encodeToString(arraySlika, Base64.DEFAULT);

            }
        }
    }

    private void addKorisnik() {

        if (korisnik != null) {
            Call<Integer> callLanguage = apiInterface.addKorisnik(korisnik);
            final Dialog dialog = LoadDialog.loadDialog(RegistracijaActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {
                        korisnikId=response.body().intValue();
                        korisnik.setKorisnikId(korisnikId);
                        Toast.makeText(RegistracijaActivity.this, "Korisnik Id"+ korisnikId, Toast.LENGTH_SHORT).show();

                         startIntent();
                    }

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(RegistracijaActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    }



    private void addVozac() {
        if (vozac != null) {
            Call<Integer> callLanguage = apiInterface.addVozac(vozac);
            final Dialog dialog = LoadDialog.loadDialog(RegistracijaActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body()!=null) {
                        vozacId=response.body().intValue();
                        vozac.setVozacId(vozacId);
                        Toast.makeText(RegistracijaActivity.this, "Vozac Id"+ vozacId, Toast.LENGTH_SHORT).show();

                         startIntent();
                    }

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(RegistracijaActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    }

    public void startIntent(){
        Intent intent = new Intent(RegistracijaActivity.this, LogovanjeActivity.class);
        startActivity(intent);
    }

}
