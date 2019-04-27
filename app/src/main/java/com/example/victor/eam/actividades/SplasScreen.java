package com.example.victor.eam.actividades;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.victor.eam.LoginActivity;
import com.example.victor.eam.R;

public class SplasScreen extends AppCompatActivity {
    CountDownTimer tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas_screen);
        tiempo=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent miIntent=new Intent(SplasScreen.this,LoginActivity.class);
                startActivity(miIntent);
                finish();
            }
        };
        tiempo.start();
    }

}

