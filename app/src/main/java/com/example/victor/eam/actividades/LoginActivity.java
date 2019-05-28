package com.example.victor.eam.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.eam.R;
import com.example.victor.eam.director_programa.PrincipalDirectorPrograma;
import com.example.victor.eam.docente.PrincipalDocente;
import com.example.victor.eam.entidades.VolleySingleton;
import com.example.victor.eam.estudiante.PrincipalEstudiante;
import com.example.victor.eam.registro_control.PrincipalRegistroControl;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText campoCodigo, campoCedula;
    String ip;
    RequestQueue request;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ip = getApplicationContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getApplicationContext());
        campoCodigo = findViewById(R.id.campoCodigoLogin);
        campoCedula = findViewById(R.id.campoCedulaLogin);
    }

    public void ingresar(View view) {

        final String codigo = campoCodigo.getText().toString();
        final String cedula = campoCedula.getText().toString();

        if (!codigo.equals("") && !cedula.equals("")){
            String url;
            url = ip + getApplicationContext().getString(R.string.ipLogin);
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    switch (response){
                        case "director":
                            Intent intent1 = new Intent(getApplicationContext(),PrincipalDirectorPrograma.class);
                            guardaCredenciales(true,cedula);
                            startActivity(intent1);
                            finish();
                            break;//campos

                        case "estudiante":
                            Intent intent3 = new Intent(getApplicationContext(),PrincipalEstudiante.class);
                            guardaCredenciales(true,cedula);
                            startActivity(intent3);
                            finish();
                            break;

                        case "registroycontrol":
                            Intent intent2 = new Intent(getApplicationContext(),PrincipalRegistroControl.class);
                            guardaCredenciales(true,cedula);
                            startActivity(intent2);
                            finish();
                            break;

                        case "docente":
                            Intent intent4 = new Intent(getApplicationContext(),PrincipalDocente.class);
                            guardaCredenciales(true,cedula);
                            startActivity(intent4);
                            finish();
                            break;

                            default:
                                Toast.makeText(LoginActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                                break;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error response: " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("codigo", codigo);
                    parametros.put("cedula", cedula);
                    Log.i("--------PARAMETROS ", parametros.toString());
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardaCredenciales(boolean b, String cedula) {
        SharedPreferences preferencesCedula = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = preferencesCedula.edit();
        user.putString("credenciales", cedula);
        user.commit();
    }
}
