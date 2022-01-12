package com.example.taxi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxi.R;
import com.example.taxi.api.ApiClient;
import com.example.taxi.api.ApiInterface;
import com.example.taxi.dialog.LoadDialog;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OcenaActivity extends AppCompatActivity implements View.OnClickListener{
RatingBar ratingBar;

int voznjaId;
int ocena;
String komentar;
    TextView inputKomentar;
ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocena);
       ratingBar= findViewById(R.id.ratingBar);
        inputKomentar=findViewById(R.id.inputKomentar);

        findViewById(R.id.buttonOcena).setOnClickListener(this);
        Bundle bundle=getIntent().getExtras();

            voznjaId=bundle.getInt("voznjaId");
//            Toast.makeText(OcenaActivity.this,voznjaId, Toast.LENGTH_SHORT).show();
        apiInterface = ApiClient.getClient(ApiInterface.class);

    }

    @Override
    public void onClick(View v) {
        ocena=(int)ratingBar.getRating();

        komentar=inputKomentar.getText().toString();
        if(komentar.equals("")){
        komentar="Nema komentara";}

        updateVoznja();
    }
    private void updateVoznja() {
 Call<Void> callLanguage = apiInterface.updateVoznja(voznjaId,ocena,komentar);
            final Dialog dialog = LoadDialog.loadDialog(OcenaActivity.this);
            dialog.show();
            callLanguage.enqueue(new Callback<Void>()
            {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response)
                {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if(response.code() == HttpURLConnection.HTTP_OK){
                        Toast.makeText(OcenaActivity.this,"Uspešno uneti utisci o vožnji", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OcenaActivity.this,"Neuspešno uneti utisci o vožnji", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t)
                {
                    t.printStackTrace();
                    Toast.makeText(OcenaActivity.this, R.string.errorInternet, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }
    }
