package com.example.victor.eam.registro_control;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreacionMaterias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreacionMaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreacionMaterias extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button btnRegistrar, btnSumarHoras, btnRestarHoras, btnSumarCreditos, btnRestarCreditos, btnActa, btnPrerrequisitos;
    TextView campoHoras, campoCreditos,campoNombre,campoCosto;
    Spinner spinnerEntorno;
    private Dialog dialogoPrerequisitos;
    private Dialog dialogActa;
    private RequestQueue request;
    private StringRequest stringRequest;

    ArrayList arrayEntornos;
    ArrayList arrayMaterias;

    //VARIABLES
    String prerrequisitos;
    String actaDescriptiva;
    String ip;
    String entorno;

    Spinner spinnerMaterias;
    int contadorHoras = 0, contadorCreditos = 0;

    public CreacionMaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreacionMaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static CreacionMaterias newInstance(String param1, String param2) {
        CreacionMaterias fragment = new CreacionMaterias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_creacion_materias, container, false);
        ip = getContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getContext());

        btnActa = vista.findViewById(R.id.btnActaDescriptiva);
        btnActa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupActa();
            }
        });
        btnPrerrequisitos = vista.findViewById(R.id.btnPrerrequisitos);
        btnPrerrequisitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupPrerrequisitos();
            }
        });
        btnRestarCreditos = vista.findViewById(R.id.btnRestarCreditos);
        btnRestarCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restar(1);
            }
        });
        btnRestarHoras = vista.findViewById(R.id.btnRestarHoras);
        btnRestarHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restar(2);
            }
        });
        btnSumarCreditos = vista.findViewById(R.id.btnSumarCreditos);
        btnSumarCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumar(1);
            }
        });
        btnSumarHoras = vista.findViewById(R.id.btnSumarHoras);
        btnSumarHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumar(2);
            }
        });
        campoCreditos = vista.findViewById(R.id.campoCreditos);
        campoNombre = vista.findViewById(R.id.campoNombreMateria);
        campoCosto = vista.findViewById(R.id.campoCosto);
        campoHoras = vista.findViewById(R.id.campoHoras);
        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
        spinnerEntorno = vista.findViewById(R.id.spinnerEntorno);

        dialogoPrerequisitos = new Dialog(this.getContext());
        dialogActa = new Dialog(this.getContext());

        cargarEntorno();
        return vista;
    }

    private void cargarPrerrequisitos() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaMaterias);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarEntorno() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaEntornos);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void registrar() {
        String url;
        url = ip + getContext().getString(R.string.ipRegistroMaterias);
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("registra")) {
                    //Log.i("********RESULTADO", "Respuesta server" + response);
                    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error response: " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                /*
    * $intensidadhoraria= $_POST["intensidadhoraria"];
	$numerocreditos= $_POST["nucreditoacademico"];
	$actadescriptiva= $_POST["actadescriptiva"];
	$costomateria= $_POST["costomateria"];
	$entornomateria= $_POST["entorno_identorno"];
	$prerrequisito= $_POST["prer_idprerequisito"];u
    $materia= $_POST["materia_idmateria"];*/


                String nombre = campoNombre.getText().toString();
                String costoMateria = campoCosto.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("$nombre", nombre);
                parametros.put("$intensidadhoraria", String.valueOf(contadorHoras));
                parametros.put("$numerocreditos", String.valueOf(contadorCreditos));
                parametros.put("$actadescriptiva", String.valueOf(actaDescriptiva));
                parametros.put("$costomateria", costoMateria);
                parametros.put("$entornomateria", String.valueOf(entorno));
                parametros.put("$prerrequisito", String.valueOf(prerrequisitos));
                parametros.put("$numerocreditos", String.valueOf(contadorCreditos));

                Log.i("--------PARAMETROS ", parametros.toString());
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);
    }


    private void sumar(int i) {
        switch (i) {
            case 1:
                //+
                contadorCreditos ++;
                campoCreditos.setText(""+contadorCreditos);
                break;

            case 2:
                contadorHoras ++;
                campoHoras.setText(""+contadorHoras);
                break;
        }

    }

    private void restar(int i) {
        switch (i) {
            case 1:
                contadorCreditos --;
                if (contadorCreditos <=0){
                    contadorCreditos = 0;
                }
                campoCreditos.setText(""+contadorCreditos);
                break;

            case 2:
                contadorHoras --;
                if (contadorHoras <=0){
                    contadorHoras = 0;
                }
                campoHoras.setText(""+contadorHoras);
                break;
        }
    }

    private void showPopupActa() {
        Button aceptar, cancelar;
        EditText campoActa;

        try {
            dialogActa.setContentView(R.layout.popup_redactaracta);
            Objects.requireNonNull(dialogActa.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogActa.show();
        } catch (Exception e) {
            Log.i("Error ", e.toString());
        }

        campoActa = dialogActa.findViewById(R.id.campoActaDescrptiva);
        actaDescriptiva = campoActa.getText().toString();
        aceptar = dialogActa.findViewById(R.id.btnAceptarActa);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Captura el dato del campo acta
            }
        });

        cancelar = dialogActa.findViewById(R.id.btnCancelarActa);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogActa.hide();
                Toast.makeText(getContext(), "No se ha guardado el acta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupPrerrequisitos() {
        Button aceptar, cancelar;

        cargarPrerrequisitos();

        try {
            dialogoPrerequisitos.setContentView(R.layout.popup_prerrequisito);
            Objects.requireNonNull(dialogoPrerequisitos.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogoPrerequisitos.show();
        } catch (Exception e) {
            Log.i("Error ", e.toString());
        }


        spinnerMaterias = dialogoPrerequisitos.findViewById(R.id.spinnerPrerrequisitos);

        spinnerMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prerrequisitos = String.valueOf(position + 1 );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aceptar = dialogoPrerequisitos.findViewById(R.id.btnAceptarPrerrequisitos);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar la materia que escoja en el spinner
            }
        });

        cancelar = dialogoPrerequisitos.findViewById(R.id.btnCancelarPrerrequisitos);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoPrerequisitos.hide();
                Toast.makeText(getContext(), "No ha elegido nungiun prerrequisito ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//=================================================================================================================================
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonEntorno = response.optJSONArray("entorno");
        JSONObject jsonObjectEntorno;
        arrayEntornos = new ArrayList();
        try {
            for (int i = 0; i < jsonEntorno.length(); i++) {
                jsonObjectEntorno = jsonEntorno.getJSONObject(i);
                arrayEntornos.add(jsonObjectEntorno.getString("entorno"));
            }

            ArrayAdapter<CharSequence> adapterEntorno = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayEntornos);
            spinnerEntorno.setAdapter(adapterEntorno);
            spinnerEntorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    entorno = String.valueOf(position + 1 );
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

        }

        /////////////////////////////////////////////////////////////////////////////////////////////
        JSONArray jsonMateria = response.optJSONArray("materia");
        JSONObject jsonObjectMateria;
        arrayMaterias = new ArrayList();
        try {
            for (int i = 0; i < jsonMateria.length(); i++) {
                jsonObjectMateria = jsonMateria.getJSONObject(i);
                arrayMaterias.add(jsonObjectMateria.getString("nombre"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item, arrayMaterias);
            spinnerMaterias.setAdapter(adapter);
        } catch (Exception e) {

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
