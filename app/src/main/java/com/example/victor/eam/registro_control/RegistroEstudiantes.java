package com.example.victor.eam.registro_control;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.eam.R;

import com.example.victor.eam.entidades.ProgramaVo;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.Field;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroEstudiantes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroEstudiantes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroEstudiantes extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RequestQueue request;
    private StringRequest stringRequest;

    int accion;
    EditText campoCodigo, campoCedula, campoNombre, campoEstado, campoDireccion, campoTelefono, campoCoreo;
    Spinner spinnerFacultad, spinnerPrograma, spinnerSemestre;
    TextView campoFecha;
    Button btnRegistrar;
    String ip, programaAcademico, semestre;
    ArrayList arrayFacultades;
    ArrayList arrayProgramas;
    ArrayList arraySemestres;

    ProgramaVo programaVo;
    ArrayList<ProgramaVo>arrayListProgramas;

    public RegistroEstudiantes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroEstudiantes.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroEstudiantes newInstance(String param1, String param2) {
        RegistroEstudiantes fragment = new RegistroEstudiantes();
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
        View vista = inflater.inflate(R.layout.fragment_registro_estudiantes, container, false);
        ip = getContext().getString(R.string.ip);
        campoCodigo = vista.findViewById(R.id.campoCodigo);
        campoCedula = vista.findViewById(R.id.campoCedula);
        campoNombre = vista.findViewById(R.id.campoNombre);
        campoDireccion = vista.findViewById(R.id.campoDireccion);
        campoTelefono = vista.findViewById(R.id.campoTelefono);
        campoCoreo = vista.findViewById(R.id.campoCorreo);
        spinnerFacultad = vista.findViewById(R.id.spinnerFacultad);
        spinnerPrograma = vista.findViewById(R.id.spinnerPrograma);
        spinnerSemestre = vista.findViewById(R.id.spinnerSemestre);
        campoFecha = vista.findViewById(R.id.campoFecha);
        campoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarFecha();
            }
        });
        btnRegistrar = vista.findViewById(R.id.btnTegirtrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
        request = Volley.newRequestQueue(getContext());
        cargarFacultad();
        cargarSemestre();
        asignarCodigo();
        return vista;
    }

    private void asignarCodigo() {
        int codigo = (int) (Math.random()*9999+1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        campoCodigo.setText(fecha+codigo);
        Toast.makeText(getContext(), "Codigo: "+ fecha+codigo, Toast.LENGTH_SHORT).show();
    }

    private void cargarFacultad() {
        accion = 1;
        String url;
        url = ip + getContext().getString(R.string.ipFacultades);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarPrograma(int position) {
        accion = 2;
        String url;
        url = ip + getContext().getString(R.string.ipProgramas)+position ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void cargarSemestre(){
        String url;
        url = ip+getContext().getString(R.string.ipConsultarSemestre);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void registrar() {

        final String codigo = campoCodigo.getText().toString();
        final String cedula = campoCedula.getText().toString();
        final String nombre = campoNombre.getText().toString();
        final String fecha = campoFecha.getText().toString();
        final String direccion = campoDireccion.getText().toString();
        final String telefono = campoTelefono.getText().toString();
        final String correo = campoCoreo.getText().toString();

        if (!codigo.equals("") && !cedula.equals("") && !nombre.equals("") && !fecha.equals("") && !direccion.equals("") && !telefono.equals("") && !correo.equals("") && !programaAcademico.equals("")){
            String url;
            url = ip + getContext().getString(R.string.ipRegistro);
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("registra")) {
                        Log.i("********RESULTADO", "Respuesta server" + response);
                     //   Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                    } else {
                     //   Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                 //   Toast.makeText(getContext(), "Error response: " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {



                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("codigoestudiante", codigo);
                    parametros.put("cedulaestudiante", cedula);
                    parametros.put("nombreestudiante", nombre);
                    parametros.put("fechaNacimiento", fecha);
                    parametros.put("estadoestudiante", "1");
                    parametros.put("direccionestudiante", direccion);
                    parametros.put("telefonoestudiante", telefono);
                    parametros.put("correoelectronico", correo);
                    parametros.put("pacademico_idpacademico", programaAcademico);
                    parametros.put("semestre_numerosemestre", semestre);
                    Log.i("--------PARAMETROS ", parametros.toString());
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);
        }else {
            Toast.makeText(getContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }


    }




    private void capturarFecha() {
        Calendar mcurrentDate = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        campoFecha.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
        mDatePicker.setInverseBackgroundForced(false);
        mDatePicker.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
        switch (accion) {
            case 1:
                JSONArray jsonFacultad = response.optJSONArray("facultad");
                JSONObject jsonObjectFacultad;
                arrayFacultades = new ArrayList();
                try {
                    for (int i = 0; i < jsonFacultad.length(); i++) {
                        jsonObjectFacultad = jsonFacultad.getJSONObject(i);
                        arrayFacultades.add(jsonObjectFacultad.getString("facultad"));
                    }

                    ArrayAdapter<CharSequence> adapterFacultad = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayFacultades);
                    spinnerFacultad.setAdapter(adapterFacultad);
                    spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cargarPrograma(position +1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (Exception e) {

                }
                break;

            case 2:
                programaVo = null;
                JSONArray jsonPrograma = response.optJSONArray("programa");
                JSONObject jsonObjectPrograma;
                arrayProgramas = new ArrayList();
                arrayListProgramas =  new ArrayList<>();
                try {
                    for (int i = 0; i < jsonPrograma.length(); i++) {
                        programaVo = new ProgramaVo();
                        jsonObjectPrograma = jsonPrograma.getJSONObject(i);
                        programaVo.setNombre(jsonObjectPrograma.getString("programa"));
                        programaVo.setId(jsonObjectPrograma.getString("id"));
                        arrayListProgramas.add(programaVo);
                    }

                    ArrayAdapter<CharSequence> adapterPrograma = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayListProgramas);
                    spinnerPrograma.setAdapter(adapterPrograma);
                    spinnerPrograma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String programa = arrayListProgramas.get(spinnerPrograma.getPositionForView(view)).getId();
                            programaAcademico = programa;
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (Exception e) {

                }
                break;
        }

        JSONArray jsonSemestre = response.optJSONArray("semestre");
        JSONObject jsonObjectSemestre;
        arraySemestres = new ArrayList();
        try {
            for (int i = 0; i < jsonSemestre.length(); i++) {
                jsonObjectSemestre = jsonSemestre.getJSONObject(i);
                arraySemestres.add(jsonObjectSemestre.getString("nombre"));
            }
            ArrayAdapter<CharSequence> adapterSemestre = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arraySemestres);
            spinnerSemestre.setAdapter(adapterSemestre);
            spinnerSemestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    semestre = String.valueOf(position+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

        }



    }

    @Override
    public void onErrorResponse(VolleyError error) {
    //    Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
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
