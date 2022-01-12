package com.example.taxi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Voznja;
import com.example.taxi.dialog.LoadDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikacijeActivity extends AppCompatActivity {
    private ApiInterface apiInterface;
    ArrayList<Voznja> voznje=new ArrayList<>();
    private int voznjaId;
    String naslov, telo;
    EditText obavestenje;
 String stringObavestenje;
    Bundle sacuvaniPodaci;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikacije);
        obavestenje=findViewById(R.id.inputNotifikacija);
        obavestenje.setFocusable(false);

            Bundle bundle=getIntent().getExtras();
       sharedpreferences=getSharedPreferences("preferences", Context.MODE_PRIVATE);
        if (bundle != null) {
            if (bundle.containsKey("naslov")) {
                naslov=bundle.getString("naslov");
            }
            if (bundle.containsKey("telo")) {
                telo=bundle.getString("telo");
            }
            if (bundle.containsKey("voznjaId")) {
                voznjaId=bundle.getInt("voznjaId");
                Log.i("voznjaID"," "+voznjaId);
            }
        }
        else {
            if(sharedpreferences!=null){
            stringObavestenje=sharedpreferences.getString("obavestenje","nema obavestenja");}
        Log.i("sharedPreferencesN",stringObavestenje);}
     /*   Intent intent = getIntent();
        voznjaId=intent.getIntExtra("voznjaId",0); */
     //   Toast.makeText(NotifikacijeActivity.this,"Voznja id je "+voznjaId,Toast.LENGTH_LONG).show();
        apiInterface = ApiClient.getClient(ApiInterface.class);

        if(naslov !=null & telo!=null){
            stringObavestenje=naslov+"\n"+telo+"\n";
       //     obavestenje.append("\n");
          //  obavestenje.setText(telo);

        }
        if(voznjaId!=0 && naslov.equals("Vožnja je prihvaćena")){
          stringObavestenje=findVoznja();
        Toast.makeText(this,"Voznja id je razlicita od 0",Toast.LENGTH_LONG).show();}
        else {
            if (stringObavestenje.equals("nema obavestenja") || stringObavestenje == null)
                stringObavestenje = "Trenutno nemate obaveštenja za prikaz.";
            obavestenje.setText(stringObavestenje);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("obavestenje",stringObavestenje);
            editor.commit();

        }
    }




    private String findVoznja() {
        Call<ArrayList<Voznja>> callLanguage = apiInterface.getVoznja(voznjaId,"voznja");

        final Dialog dialog = LoadDialog.loadDialog(NotifikacijeActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Voznja>>() {
            @Override
            public void onResponse(Call<ArrayList<Voznja>> call, Response<ArrayList<Voznja>> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {

                    voznje = response.body();
                    if(!voznje.isEmpty()){
                        stringObavestenje+= " Vozač sa sledećim podacima stiže na Vašu adresu: "+ voznje.get(0).getVozac().getIme()+" "+voznje.get(0).getVozac().getPrezime()+"\n"
                                +"Godine starosti: "+voznje.get(0).getVozac().getGodineStarosti()+"\n"
                                +"Godine iskustva: "+voznje.get(0).getVozac().getGodineIskustva()+"\n"
                                +"Broj telefona: "+voznje.get(0).getVozac().getTelefon()+"\n";
                        Log.i("voznjeucitane","voznje su ucitane"+voznje.get(0).getVozac().getGodineStarosti());

                        if(voznje.get(0).getVozac().getStraniJezik()!=null) stringObavestenje+="Strani jezik koji govori: "+voznje.get(0).getVozac().getStraniJezik()+"\n";
                        stringObavestenje+="Podaci o vozilu: "+"\n"+
                                "Model i tip vozila: "+voznje.get(0).getVozac().getVozilo().getModelITipVozila()+"\n"+
                                "Registarski broj: "+voznje.get(0).getVozac().getVozilo().getRegistarskiBroj()+"\n"+
                                "Broj sedišta: "+voznje.get(0).getVozac().getVozilo().getBrojSedista()+"\n";
                        if(voznje.get(0).getVozac().getVozilo().getEkoloskoVozilo()!=0)stringObavestenje+="Vozilo zadovoljava propisane ekološke standarde"+"\n";
                        if(voznje.get(0).getVozac().getVozilo().getSedisteZaBebe()!=0)stringObavestenje+="Vozilo poseduje sedište za bebe"+"\n";
                        Toast.makeText(NotifikacijeActivity.this,stringObavestenje, Toast.LENGTH_LONG).show();}

                    obavestenje.setText(stringObavestenje);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("obavestenje",stringObavestenje);
                    editor.commit();

                }

                if(voznje.isEmpty()){
                    Toast.makeText(NotifikacijeActivity.this,"Nema voznji sa tim id!", Toast.LENGTH_LONG).show();}


            }

            @Override
            public void onFailure(Call<ArrayList<Voznja>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(NotifikacijeActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
return stringObavestenje;
    }
}