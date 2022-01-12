package com.example.taxi.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taxi.AlertReceiver;
import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozilo;
import com.example.taxi.data.Voznja;
import com.example.taxi.data.VoznjaDetalji;
import com.example.taxi.dialog.LoadDialog;
import com.google.android.gms.maps.GoogleMap;

import java.net.HttpURLConnection;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodaciActivity extends AppCompatActivity implements View.OnClickListener {
   ArrayList<String> adreseOpstine =new ArrayList<>();
    ArrayList<String> adreseUlice =new ArrayList<>();
 EditText inputDatum, inputVreme;
 TextView labelVoznja,labelPoruka;
 Vozilo vozilo;
 Korisnik korisnik=new Korisnik();
 ArrayList<Voznja> voznje=new ArrayList<>();
    EditText inputOpstina, inputUlica;
    Calendar vremeKraj,vremePocetak=Calendar.getInstance();
    ApiInterface apiInterface;
    String ime,prezime,date2;
    int id,count=0, count2=0,korisnikId;
private NotificationManagerCompat notificationManager;
    Timestamp vremeAlarm;
    ImageButton buttonAdd, buttonAdd2;
GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podaci);
        initComponents();
        if(getIntent().getExtras()!=null){
        Bundle bundle=getIntent().getExtras();
        apiInterface = ApiClient.getClient(ApiInterface.class);
        ime=bundle.getString("ime");
        prezime=bundle.getString("prezime");
        korisnikId=bundle.getInt("korisnikId");
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setKorisnikId(korisnikId);}
    notificationManager=NotificationManagerCompat.from(this);
     //   SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView);
     //   mapFragment.getMapAsync(this);
        findVoznja(korisnikId);

    }


   public void initComponents(){
       inputDatum=(EditText)findViewById(R.id.inputDatum);
       inputDatum.setOnClickListener(this);
       inputVreme=(EditText)findViewById(R.id.inputVreme);
       inputVreme.setOnClickListener(this);
       (findViewById(R.id.buttonOcena)).setOnClickListener(this);
       labelPoruka=(TextView)findViewById(R.id.labelPoruka);
       labelVoznja=(TextView)findViewById(R.id.labelVoznja);
         inputOpstina =(EditText) findViewById(R.id.inputPocetnaAdresa);
      inputUlica = (EditText) findViewById(R.id.inputKrajnjaAdresa);
      buttonAdd= (ImageButton) findViewById(R.id.buttonAdd);
     buttonAdd.setOnClickListener(this);
     buttonAdd2=findViewById(R.id.buttonAdd2);
     buttonAdd2.setOnClickListener(this);
       (findViewById(R.id.buttonPregled)).setOnClickListener(this);
       (findViewById(R.id.buttonBack)).setOnClickListener(this);
       (findViewById(R.id.buttonExit)).setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Timestamp vremeAlarm, int voznjaId){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if(voznjaId!=0){
        Intent intent=new Intent(this, AlertReceiver.class);
        intent.putExtra("voznjaId",voznjaId);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,vremeAlarm.getTime(),pendingIntent);}
    }
    private void createSpinner(){
        Spinner spinnerAdrese=(Spinner) findViewById(R.id.spinnerAdrese);
        Spinner spinnerUlice=(Spinner) findViewById(R.id.spinnerUlice);
    spinnerAdrese.setVisibility(View.VISIBLE);
    buttonAdd.setVisibility(View.VISIBLE);
        spinnerUlice.setVisibility(View.VISIBLE);
        buttonAdd2.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,adreseOpstine);
        spinnerAdrese.setAdapter(adapter);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,adreseUlice);
        spinnerUlice.setAdapter(adapter2);
        spinnerAdrese.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opstina=(String)parent.getSelectedItem();
                inputOpstina.setVisibility(View.VISIBLE);
                inputOpstina.setText(opstina);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerUlice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ulica=(String)parent.getSelectedItem();
                inputUlica.setVisibility(View.VISIBLE);
                inputUlica.setText(ulica);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private Voznja createVoznja() {
        Voznja voznja=new Voznja();


        if(vozilo == null){
            return null;
        }
     //   voznja.setVozilo(vozilo);
     //   Toast.makeText(PodaciActivity.this,voznja.getVozilo().getOpstina()+voznja.getVozilo().getTipVozila(),Toast.LENGTH_LONG).show();

      //  String str=voznja.getVozilo().getModelITipVozila();
        if(korisnik== null){
            return null;
        }
        voznja.setKorisnik(korisnik);
    //    Toast.makeText(PodaciActivity.this,voznja.getKorisnik().getIme()+voznja.getKorisnik().getPrezime(),Toast.LENGTH_LONG).show();

      //  str+="korisnik"+voznja.getKorisnik().getPrezime()+" "+voznja.getKorisnik().getIme();
        VoznjaDetalji voznjaDetalji=createVoznjaDetalji();
        if(voznjaDetalji == null){
            Toast.makeText(PodaciActivity.this,"VoznjaDetalji je prazna",Toast.LENGTH_SHORT).show();
            return null;
        }
        voznja.setVoznjaDetalji(voznjaDetalji);
      //  Toast.makeText(PodaciActivity.this,voznja.getVoznjaDetalji().getAdresaOpstina()+"ulica"+voznja.getVoznjaDetalji().getAdresa()+" "+voznja.getVoznjaDetalji().getVremeVoznje()+" "+voznja.getVoznjaDetalji().getVremeRezervacije()+"vreme kraj:"+voznja.getVoznjaDetalji().getVremeKraj(),Toast.LENGTH_SHORT).show();
  // labelPoruka.setText(voznja.getVoznjaDetalji().getAdresaOpstina()+"ulica"+voznja.getVoznjaDetalji().getAdresa()+" "+voznja.getVoznjaDetalji().getVremeVoznje()+" "+voznja.getVoznjaDetalji().getVremeRezervacije()+"vreme kraj:"+voznja.getVoznjaDetalji().getVremeKraj());

    /*  labelPoruka.setText(voznja.getKorisnik().getIme()+" "+voznja.getKorisnik().getPrezime()+" "+voznja.getKorisnik().getKorisnikId()+" "+voznja.getVozilo().getTipVozila()+" "+voznja.getVozilo().getOpstina()+" "+voznja.getVozilo().getVoziloId()+" "+
              voznja.getVoznjaDetalji().getAdresaOpstina()+" "+voznja.getVoznjaDetalji().getAdresa()+" vreme voznje  "+voznja.getVoznjaDetalji().getVremeVoznje()+" vreme rezervacije:"+voznja.getVoznjaDetalji().getVremeRezervacije()+" "+voznja.getVoznjaDetalji().getVremeKraj()+" "+voznja.getVoznjaDetalji().getVoznjaDetaljiId());
        */
     return voznja;
    }

  /*  private Korisnik findKorisnik(){

        Call<Korisnik> callLanguage = apiInterface.getKorisnik(ime,prezime);
        final Dialog dialog = LoadDialog.loadDialog(PodaciActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<Korisnik>()
        {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                   id=response.body().getKorisnikId();
                   korisnik=response.body();

                   Toast.makeText(PodaciActivity.this,"korisnik id"+id+" "+korisnik.getKorisnikId(),Toast.LENGTH_SHORT).show();

                }else{
                    labelVoznja.setText("Greska pri uzimanju podataka o korisniku");
                }
            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(PodaciActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();
                    }
        });
        return korisnik;
    } */


    private void findVozilo(){

        String opstina=inputOpstina.getText().toString();

            Call<Vozilo> callLanguage = apiInterface.getVozilo(id);
            final Dialog dialog = LoadDialog.loadDialog(PodaciActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Vozilo>()
                {
                    @Override
                    public void onResponse(Call<Vozilo> call, Response<Vozilo> response)
                    {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                          vozilo =response.body();
                          addVoznja();
                        }else{
                            labelVoznja.setText(getString(R.string.noVozilo));
                        }
                    }

                    @Override
                    public void onFailure(Call<Vozilo> call, Throwable t)
                    {
                        t.printStackTrace();
                        Toast.makeText(PodaciActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing())
                            dialog.dismiss();

                    }
                });



    }


    private VoznjaDetalji createVoznjaDetalji() {
        VoznjaDetalji voznjaDetalji = new VoznjaDetalji();
        if(inputOpstina.getText().toString().isEmpty()){
            inputOpstina.setError("Polje opština ne sme biti prazno!");
            return null;
        }
   //     voznjaDetalji.setAdresaOpstina(inputOpstina.getText().toString());

        if(inputUlica.getText().toString().isEmpty()) {
            inputUlica.setError("Polje ulica ne sme biti prazno!");
            return null;
        }
    //    voznjaDetalji.setAdresa(inputUlica.getText().toString());

        if(inputDatum.getText().toString().isEmpty()){
            inputDatum.setError("Polje datum ne sme biti prazno!");
            return null;
        }
        if(inputVreme.getText().toString().isEmpty()){
            inputVreme.setError("Polje vreme ne sme biti prazno!");
            return null;
        }
        String timestamp=date2+" "+inputVreme.getText().toString()+":00";
        Timestamp time1=Timestamp.valueOf(timestamp);
        Timestamp time3=Timestamp.valueOf(timestamp);


        Timestamp vremeRezervacije=new Timestamp(System.currentTimeMillis());
        Timestamp vremeRezervacije2=new Timestamp(System.currentTimeMillis());
        long tt=vremeRezervacije.getTime();
        long mm=30*60*1000;
        Timestamp time2=vremeRezervacije2;
      //  time2.setDate(vremeRezervacije.getDate());
        time2.setTime(tt+mm);


      //  vremeAlarm=new Timestamp(System.currentTimeMillis());
            if((time1.compareTo(time2))>=0){
            //    voznjaDetalji.setVremeVoznje(timestamp);
                vremeAlarm=time3;
                vremeAlarm.setTime(time1.getTime()+mm);
              //  vremeAlarm=new Timestamp(System.currentTimeMillis());
                Toast.makeText(PodaciActivity.this,"vreme voznje:"+time1+"vreme rezervacije"+vremeRezervacije+"vreme alarma:"+vremeAlarm,Toast.LENGTH_LONG).show();

                return voznjaDetalji;}
            else {labelVoznja.setText("Voznju morate rezervisati najmanje pola sata pre termina voznje ");
                return null;}
    }

    private void findVoznja(int id){
        Call<ArrayList<Voznja>> callLanguage = apiInterface.getVoznja(id,"korisnik");

        final Dialog dialog = LoadDialog.loadDialog(PodaciActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Voznja>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Voznja>> call, Response<ArrayList<Voznja>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    voznje =response.body();
                    count=0;
                    Toast.makeText(PodaciActivity.this,"broj adresa"+response.body().size()+" "+voznje.size(),Toast.LENGTH_SHORT).show();
                    while (voznje.size()>count) {
                 //  zbog menjanja parametara     adreseOpstine.add(voznje.get(count).getVoznjaDetalji().getAdresaOpstina());
                 //      adreseUlice.add(voznje.get(count).getVoznjaDetalji().getAdresa());
                        count=count+1;
                    }
            if(!voznje.isEmpty()){
                    createSpinner();}
            else  {inputOpstina.setVisibility(View.VISIBLE);
                   inputUlica.setVisibility(View.VISIBLE);}
                 //   Toast.makeText(PodaciActivity.this,"adresa opstine"+adreseOpstine.get(1)+" "+voznje.size(),Toast.LENGTH_SHORT).show();
                }
                else{
                    labelVoznja.setText(getString(R.string.noVoznje));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Voznja>> call, Throwable t)
            {
                t.printStackTrace();
               Toast.makeText(PodaciActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.inputDatum:
                datePicker(inputDatum);
                break;
            case R.id.inputVreme:
                timePicker(inputVreme);
                break;
            case R.id.buttonAdd:
                inputOpstina.setVisibility(View.VISIBLE);
                inputOpstina.getText().clear();
                break;
            case R.id.buttonAdd2:
                inputUlica.setVisibility(View.VISIBLE);
                inputUlica.getText().clear();
                break;
            case R.id.buttonPregled:
                Intent intent=new Intent(this,PregledVoznjeActivity.class);
                intent.putExtra("ime",korisnik.getIme());
                intent.putExtra("prezime",korisnik.getPrezime());
                intent.putExtra("id",korisnik.getKorisnikId());
                startActivity(intent);
                break;
            case R.id.buttonBack:
                finish();
                break;
            case R.id.buttonExit:
                this.finishAffinity();
                break;
            case R.id.buttonOcena:
              findVozilo();

                break;

        }
    }

    private void datePicker(final EditText in){
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = dayOfMonth + "." + month + "." + year + ".";
                date2=year+"-"+month+"-"+dayOfMonth;
                in.setText(date);
            }
        }, year, month, day);
        dialog.show();
    }
    private void timePicker(final EditText in){
        Calendar cal=Calendar.getInstance();
        int currentHour=cal.get(Calendar.HOUR_OF_DAY);
        int currentMinute =cal.get(Calendar.MINUTE);

        TimePickerDialog dialog=new TimePickerDialog(PodaciActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    in.setText(String.format("%02d:%02d",hourOfDay,minute));
            }
        },currentHour,currentMinute,false);
        dialog.show();
    }

    private void addVoznja() {
        final Voznja voznja = createVoznja();
        if(voznja != null){
            Call<Integer> callLanguage = apiInterface.makeVoznja(voznja);
            final Dialog dialog = LoadDialog.loadDialog(PodaciActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Integer>()
            {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response)
                {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                        labelVoznja.setText(getString(R.string.addDetaljiVoznje)+response.body());

                       // startAlarm(vremeAlarm,response.body());

                    }else{
                        labelVoznja.setText(getString(R.string.noDetaljiVoznje));
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t)
                {
                    t.printStackTrace();
                    Toast.makeText(PodaciActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }
    }


   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Belgrade=new LatLng(45.009911519317946, 20.444561529241707);
        map.addMarker(new MarkerOptions().position(Belgrade).title("Beograd"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Belgrade));
    } */
}

