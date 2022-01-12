package com.example.taxi.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxi.DataConverter;
import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozac;
import com.example.taxi.data.Vozilo;
import com.example.taxi.dialog.LoadDialog;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener{
Vozac vozac;
Korisnik korisnik;
EditText ime, prezime, adresa,email,brojTelefona,sifra, godineStarosti, godineIskustva, straniJezik, modelVozila, registarskiBroj,brojSedista,inputSlika;
Switch sedisteBebe, ekoloskoVozilo ;
ImageView slika;
TextView imePrezimeNaslov, korisnickoIme;
LinearLayout layoutVozac;
    Bitmap bitmap,bitmap1;
    byte[] arraySlika;
    String stringSlika,stringIme,stringPrezime,stringEmail,stringTelefon,stringSifra,stringAdresa,stringModelVozila,stringRegistarskiBroj,stringStraniJezik;
    int intEkoloskoVozilo,intSediste,intBrojSedista,intGodineStarosti,intGodineIskustva;
    Vozilo vozilo;
    Button buttonAzuriranje, buttonSlika;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        LayoutInflater layoutInflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout relativeLayout =(RelativeLayout) layoutInflater.inflate(R.layout.navigacija_naslov, null);
        vozac=(Vozac) getIntent().getSerializableExtra("vozac");
        korisnik=(Korisnik)getIntent().getSerializableExtra("korisnik");
        ime=findViewById(R.id.inputIme);
        prezime=findViewById(R.id.inputPrezime);
        adresa=findViewById(R.id.inputAdresa);
        email=findViewById(R.id.inputEmail);
        brojTelefona=findViewById(R.id.inputPhone);
        sifra=findViewById(R.id.inputSifra);
        inputSlika=findViewById(R.id.inputSlika);
        godineStarosti=findViewById(R.id.inputGodineStarosti);
        godineIskustva=findViewById(R.id.inputGodineIskustva);
        straniJezik=findViewById(R.id.inputStraniJezik);
        modelVozila=findViewById(R.id.inputModelVozila);
        registarskiBroj=findViewById(R.id.inputRegistarskiBroj);
        brojSedista=findViewById(R.id.inputBrojSedista);
        sedisteBebe=findViewById(R.id.switchSediste);
        ekoloskoVozilo=findViewById(R.id.switchVozilo);
        slika=findViewById(R.id.imageRounded);
        slika.setClipToOutline(true);
        buttonSlika=findViewById(R.id.buttonSlika);
        buttonSlika.setOnClickListener(this);
        imePrezimeNaslov=findViewById(R.id.labelImeIPrezime);

        korisnickoIme=findViewById(R.id.labelMenuKorisnickoIme);
        layoutVozac=findViewById(R.id.layoutVozac);
        buttonAzuriranje=findViewById(R.id.buttonAzuriranje);
         apiInterface = ApiClient.getClient(ApiInterface.class);

        buttonAzuriranje.setOnClickListener(this);
        if(vozac!=null){
            layoutVozac.setVisibility(View.VISIBLE);
            informacijeVozac();
        }
        if(korisnik!=null){
            informacijeKorisnik();
        }

    }




    public void informacijeVozac(){
        imePrezimeNaslov.setText(vozac.getIme()+" "+vozac.getPrezime());
        korisnickoIme.setText(vozac.getEmail());
        stringSlika=vozac.getSlika();
        bitmap= DataConverter.convertString2Image(stringSlika);
        slika.setImageBitmap(bitmap);
        ime.setText(vozac.getIme());
        prezime.setText(vozac.getPrezime());
        adresa.setText(vozac.getAdresa());
        email.setText(vozac.getEmail());
        brojTelefona.setText(vozac.getTelefon());
       sifra.setText(vozac.getSifra());
     //  inputSlika.setText(vozac.getSlika());
        godineStarosti.setText(Integer.toString(vozac.getGodineStarosti()));
       godineIskustva.setText(Integer.toString(vozac.getGodineIskustva()));
        straniJezik.setText(vozac.getStraniJezik());
        modelVozila.setText(vozac.getVozilo().getModelITipVozila());
        registarskiBroj.setText(vozac.getVozilo().getRegistarskiBroj());
        brojSedista.setText(Integer.toString(vozac.getVozilo().getBrojSedista()));
        if(vozac.getVozilo().getSedisteZaBebe()==1){
        sedisteBebe.setChecked(true);}
        if(vozac.getVozilo().getSedisteZaBebe()==0){
            sedisteBebe.setChecked(false);}
        if(vozac.getVozilo().getEkoloskoVozilo()==1){
            ekoloskoVozilo.setChecked(true);}
        if(vozac.getVozilo().getEkoloskoVozilo()==0){
            ekoloskoVozilo.setChecked(false);}



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

            if(bitmap!=null){
                arraySlika= DataConverter.convertImage2ByteArray(bitmap);
                stringSlika = Base64.encodeToString(arraySlika, Base64.DEFAULT);

            }
        }
    }



    public void informacijeKorisnik(){
     /*   stringSlika=vozac.getSlika();
        bitmap= DataConverter.convertString2Image(stringSlika);
        slika.setImageBitmap(bitmap); */
        imePrezimeNaslov.setText(korisnik.getIme()+" "+korisnik.getPrezime());
        korisnickoIme.setText(korisnik.getEmail());
        ime.setText(korisnik.getIme());
        prezime.setText(korisnik.getPrezime());
        adresa.setText(korisnik.getAdresa());
        email.setText(korisnik.getEmail());
        brojTelefona.setText(korisnik.getTelefon());
        sifra.setText(korisnik.getSifra()); }

        public void addKorisnik(){
            if (ime.getText().toString().isEmpty()) {
                ime.setError("Polje ime ne može biti prazno!");}
            else{
                stringIme=ime.getText().toString();}
            if (prezime.getText().toString().isEmpty()) {
                prezime.setError("Polje prezime ne može biti prazno!");}
            else{ stringPrezime=prezime.getText().toString();}
            if (email.getText().toString().isEmpty()) {
                email.setError("Polje E-mail ne može biti prazno!");}
            else if (!isEmailValid(email.getText().toString())) {
                email.setError("E-mail je nevažeći. Unesite ponovo! ");}
            else{
                stringEmail=email.getText().toString();
            }

            if (sifra.getText().toString().isEmpty()) {
                sifra.setError("Polje šifra ne može biti prazno!");}
            else if (sifra.getText().length() < 6) {
                sifra.setError("Šifra mora imati više od 6 karaktera");}
            else {
                stringSifra=sifra.getText().toString();}
            if (brojTelefona.getText().toString().isEmpty()){
                brojTelefona.setError("Polje broj telefona ne može biti prazno");
            }
            else{
                stringTelefon=brojTelefona.getText().toString();}
            if (adresa.getText().toString().isEmpty()) {
                adresa.setError("Polje adresa ne može biti prazno!");
            }
            else{
                stringAdresa=adresa.getText().toString();}
            if(korisnik!=null){
            korisnik=new Korisnik(korisnik.getKorisnikId(),stringIme,stringPrezime,stringEmail,stringTelefon,stringSifra,stringAdresa,korisnik.getToken());
            Toast.makeText(ProfilActivity.this, "Korisnik Ime"+ korisnik.getIme()+korisnik.getPrezime()+korisnik.getAdresa()+korisnik.getEmail()+korisnik.getSifra()+korisnik.getTelefon(), Toast.LENGTH_SHORT).show();}


        }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void addVozac(){
     /*   if (inputSlika.getText().toString().isEmpty()) ;
        else{
            stringSlika=inputSlika.getText().toString();


        } */
        if (godineStarosti.getText().toString().isEmpty()) {
            godineStarosti.setError("Polje godine starosti ne može biti prazno!");}
        else{
            intGodineStarosti=Integer.parseInt(godineStarosti.getText().toString());}

        if (godineIskustva.getText().toString().isEmpty()) {
            godineIskustva.setError("Polje godine iskustva ne može biti prazno!");}
        else{
            intGodineIskustva=Integer.parseInt(godineIskustva.getText().toString());}

        if (!straniJezik.getText().toString().isEmpty()) {
            stringStraniJezik=straniJezik.getText().toString();}
        if (modelVozila.getText().toString().isEmpty()) {
            modelVozila.setError("Polje model i tip vozila ne može biti prazno!");}
        else{
            stringModelVozila=modelVozila.getText().toString();}

        if (brojSedista.getText().toString().isEmpty()) {
            brojSedista.setError("Polje broj sedišta ne može biti prazno!");}
        else{
            intBrojSedista=Integer.parseInt(brojSedista.getText().toString());}

        if (registarskiBroj.getText().toString().isEmpty()) {
            registarskiBroj.setError("Polje registarski broj ne može biti prazno!");}
        else{
            stringRegistarskiBroj=registarskiBroj.getText().toString();}

        if (ekoloskoVozilo.isChecked())
            intEkoloskoVozilo=1;
        else intEkoloskoVozilo=0;

        if (sedisteBebe.isChecked())
            intSediste=1;
        else intSediste=0;
        vozilo=new Vozilo(vozac.getVozilo().getVoziloId(), stringModelVozila,stringRegistarskiBroj,intEkoloskoVozilo,intSediste,intBrojSedista);
        vozac=new Vozac(vozac.getVozacId(),vozilo,stringIme,stringPrezime,stringEmail,stringTelefon,stringSifra,stringAdresa,stringSlika,intGodineStarosti,intGodineIskustva,stringStraniJezik,vozac.getStatus(),vozac.getToken());

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
            Toast.makeText(ProfilActivity.this,"Dozvola je odbijena...!", Toast.LENGTH_SHORT).show();

        }
    }
    private void updateKorisnik() {
        if (korisnik != null) {
            Call<Void> callLanguage = apiInterface.updateKorisnik(korisnik);
            final Dialog dialog = LoadDialog.loadDialog(ProfilActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.message()!=null) {
                        Toast.makeText(ProfilActivity.this," Uspešno dodat korisnik", Toast.LENGTH_SHORT).show();
                           imePrezimeNaslov.setText(korisnik.getIme()+" "+korisnik.getPrezime());
                           korisnickoIme.setText(korisnik.getEmail());
                        // startIntent();
                        returnRezultatKorisnik();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ProfilActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    }
    public void returnRezultatVozac(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("vozac",vozac);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    public void returnRezultatKorisnik(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("korisnik",korisnik);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void updateVozac() {
        if (vozac != null) {
            Call<Void> callLanguage = apiInterface.updateVozac(vozac);
            final Dialog dialog = LoadDialog.loadDialog(ProfilActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.message()!=null) {
                        Toast.makeText(ProfilActivity.this," Uspešno dodat vozac", Toast.LENGTH_SHORT).show();
                        imePrezimeNaslov.setText(vozac.getIme()+" "+vozac.getPrezime());
                        korisnickoIme.setText(vozac.getEmail());
                        stringSlika=vozac.getSlika();
                        bitmap= DataConverter.convertString2Image(stringSlika);
                        slika.setImageBitmap(bitmap);
                        returnRezultatVozac();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ProfilActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonAzuriranje:
                if(vozac!=null){
                    addKorisnik();
                    addVozac();
                    updateVozac();
                  //  Toast.makeText(ProfilActivity.this,"Button azuriranje", Toast.LENGTH_SHORT).show();

                }
                if(korisnik!=null){
                    addKorisnik();
                   updateKorisnik();
                }break;

            case R.id.buttonSlika:
                proveraDozvola();
                break;
    }
    }
}
