package com.example.victor.eam.registro_control;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.victor.eam.R;
import com.example.victor.eam.entidades.DocenteVO;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrearCursos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrearCursos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCursos extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    String ip, materia, docente;
    EditText txtNombreCurso, txtAnoCurso, txthoraInicio, txtHoraFin;
    Spinner spnMateria, spnDocente, spnDiaCurso;
    Button btnAgregar;
    ArrayList arrayMaterias;
    ArrayList<DocenteVO>arrayDocentes;
    private DocenteVO docenteVo;
    public CrearCursos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearCursos.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearCursos newInstance(String param1, String param2) {
        CrearCursos fragment = new CrearCursos();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View vista = inflater.inflate(R.layout.fragment_crear_cursos, container, false);
        ip = getContext().getString(R.string.ip);
        txtNombreCurso = vista.findViewById(R.id.txtNombreCurso);
        txtAnoCurso = vista.findViewById(R.id.txtAñoCurso);
        spnMateria = vista.findViewById(R.id.spnMateriaCurso);
        spnDocente = vista.findViewById(R.id.spnDocente);
        spnDiaCurso = vista.findViewById(R.id.spnDiaCurso);
        txtHoraFin = vista.findViewById(R.id.txtHoraFinCurso);
        txtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraFin();
            }
        });
        txthoraInicio = vista.findViewById(R.id.txtHoraInicioCurso);
        txthoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraInicio();
            }
        });
        btnAgregar = vista.findViewById(R.id.btnCrearCurso);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCurso();
            }
        });
        configurarDia();
        cargarDocente();
        cargarMaterias();
        return vista;
    }

    private void crearCurso() {
        final String nombreCurso=txtNombreCurso.getText().toString();
        final String añoEnCurso="2019";
        final String dia = spnDiaCurso.getSelectedItem().toString();
        final String horaInicio = txthoraInicio.getText().toString();
        final String horaFin =txtHoraFin.getText().toString();
        if (!nombreCurso.equals("")||!horaInicio.equals("")||!horaFin.equals("")){
            String url;
            url = ip + getContext().getString(R.string.ipRegistrarCurso);
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("registra")) {
                        Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                    } else {
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

                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("nombrecurso", nombreCurso);
                    parametros.put("anoencurso", añoEnCurso);
                    parametros.put("materia_idmateria", materia);
                    parametros.put("docente_iddocente", docente);
                    parametros.put("diacurso", dia);
                    parametros.put("horainiciocurso", horaInicio);
                    parametros.put("horafincurso", horaFin);
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);

        }else {
            Toast.makeText(getContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }

    }
    private void cargarMaterias() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaMaterias);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarDocente() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaDocentes);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void configurarDia() {
        String[] dia = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,dia);
        spnDiaCurso.setAdapter(adapter);
    }

    public void configurarHoraInicio() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txthoraInicio.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }
    public void configurarHoraFin() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtHoraFin.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
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
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonMateria = response.optJSONArray("materia");
        JSONObject jsonObjectMateria;
        arrayMaterias = new ArrayList();
        try {
            for (int i = 0; i < jsonMateria.length(); i++) {
                jsonObjectMateria = jsonMateria.getJSONObject(i);
                arrayMaterias.add(jsonObjectMateria.getString("materia"));
            }

            ArrayAdapter<CharSequence> adapterMateria = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayMaterias);
            spnMateria.setAdapter(adapterMateria);
            spnMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    materia = String.valueOf(position + 1 );
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

        }
        //----------------------------------
        docenteVo = null;
        JSONArray jsonDocente = response.optJSONArray("docente");
        JSONObject jsonObjectDocente;
        arrayDocentes = new ArrayList();
        //final ArrayList listaNombres = new ArrayList();

        try {
            for (int i = 0; i < jsonDocente.length(); i++) {
                jsonObjectDocente = jsonDocente.getJSONObject(i);
                docenteVo = new DocenteVO();
                docenteVo.setNombre(jsonObjectDocente.getString("docente"));
                docenteVo.setId(jsonObjectDocente.getString("id"));
                arrayDocentes.add(docenteVo);
            }

            ArrayAdapter<CharSequence> adapterDocente = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,arrayDocentes);
            spnDocente.setAdapter(adapterDocente);
            spnDocente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //docente = String.valueOf(position + 1 );
                    docente = arrayDocentes.get(spnDocente.getPositionForView(view)).getId();
                    Toast.makeText(getContext(), "Codigo docente: " + docente, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

        }
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
