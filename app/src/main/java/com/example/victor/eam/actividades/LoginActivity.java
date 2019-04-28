package com.example.victor.eam.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.victor.eam.R;
import com.example.victor.eam.registro_control.PrincipalRegistroControl;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void ventana(View view) {
        Intent intent = new Intent(getApplicationContext(),PrincipalRegistroControl.class);
        startActivity(intent);
    }
}
