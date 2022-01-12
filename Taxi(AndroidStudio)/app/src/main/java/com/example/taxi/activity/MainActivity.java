package com.example.taxi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.taxi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (findViewById(R.id.buttonZakazi)).setOnClickListener(this);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.e(TAG,"Greska pri generisanju tokena");
                return;}
                String token=task.getResult();
                Toast.makeText(MainActivity.this,"Token: " +token,Toast.LENGTH_LONG).show();
                Log.d(TAG,"Token: "+ token);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Token nije uspesno generisan"+e.getLocalizedMessage());
            }
        });
    }


    /**
     * method to handle the data content on clicking of notification if both notification and data payload are sent
     */
    private void handleNotificationData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("data1")) {
                Log.d(TAG, "Data1 : " + bundle.getString("data1"));
            }
            if (bundle.containsKey("data2")) {
                Log.d(TAG, "Data2 : " + bundle.getString("data2"));
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this, LogovanjeActivity.class);
        startActivity(intent);
    }
}
