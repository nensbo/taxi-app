package com.example.taxi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.taxi.R;

public class ONamaActivity extends AppCompatActivity {
    EditText oNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_nama);
    oNama=findViewById(R.id.inputONama);
    oNama.setFocusable(false);
    }
}