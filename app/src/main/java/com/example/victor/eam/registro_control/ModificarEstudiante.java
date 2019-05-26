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
import com.example.victor.eam.entidades.DocenteVO;
import com.example.victor.eam.entidades.EstudianteVO;
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
 * {@link ModificarEstudiante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarEstudiante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarEstudiante extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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

    EditText txtCodigo, txtCedula, txtNombre, txtFechaNacimiento, txtEstado, txtDireccion, txtTelefono, txtCorreo;
    Spinner spnPrograma, spnSemestre;
    Button btnBuscar, btnModificar, btnInactivar;
    String ip, programaAcademico, semestre;
    ArrayList arrayProgramas;
    ArrayList arraySemestres;
    private EstudianteVO estudiante;
    ArrayList arrayListEstudiante;
    ArrayList<EstudianteVO> arrayEstudiante;

    public ModificarEstudiante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarEstudiante.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarEstudiante newInstance(String param1, String param2) {
        ModificarEstudiante fragment = new ModificarEstudiante();
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
        request = Volley.newRequestQueue(getContext());
        ip = getContext().getString(R.string.ip);
        View vista = inflater.inflate(R.layout.fragment_modificar_estudiante, container, false);
        txtCedula = vista.findViewById(R.id.txtCedulaEstudiante);
        txtCodigo = vista.findViewById(R.id.txtCodigoEstudiante);
        txtNombre = vista.findViewById(R.id.txtNombreEstudiante);
        txtFechaNacimiento = vista.findViewById(R.id.txtFechaEstudiante);
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarFecha();
            }
        });
        txtEstado = vista.findViewById(R.id.txtEstadoEstudiante);
        txtDireccion = vista.findViewById(R.id.txtDireccion);
        txtTelefono = vista.findViewById(R.id.txtTelefono);
        txtCorreo = vista.findViewById(R.id.txtCorreo);
        spnPrograma = vista.findViewById(R.id.spnPrograma);
        spnSemestre = vista.findViewById(R.id.spnSemestre);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEstudiante();
            }
        });
        btnModificar = vista.findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEstudiante();
            }
        });
        btnInactivar = vista.findViewById(R.id.btnInactivar);
        btnInactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inactivarEstudiante();
            }
        });
       // cargarPrograma();
        cargarSemestre();
        return vista;
    }

    private void capturarFecha() {
        Calendar mcurrentDate = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        txtFechaNacimiento.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
        mDatePicker.setInverseBackgroundForced(false);
        mDatePicker.show();
    }

    private void cargarPrograma() {
        String url;
        url = ip + getContext().getString(R.string.ipProgramas);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarSemestre() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultarSemestre);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void inactivarEstudiante() {
        final String codigo = txtCodigo.getText().toString();
        final String estado = "0";

        String url;
        url = ip + getContext().getString(R.string.ipInactivarEstudiante);
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("modifico")) {
                    Log.i("********RESULTADO", "Respuesta server" + response);
                    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error response: " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> parametros = new HashMap<>();
                parametros.put("codigoestudiante",codigo);
                parametros.put("estadoestudiante",estado);
                Log.i("--------PARAMETROS ", parametros.toString());
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);

    }

    private void modificarEstudiante() {
        final String codigo = txtCodigo.getText().toString();
        final String cedula = txtCedula.getText().toString();
        final String nombre = txtNombre.getText().toString();
        final String fecha = txtFechaNacimiento.getText().toString();
        final String estado = txtEstado.getText().toString();
        final String direccion = txtDireccion.getText().toString();
        final String telefono = txtTelefono.getText().toString();
        final String correo = txtCorreo.getText().toString();

        if (!codigo.equals("") && !cedula.equals("") && !nombre.equals("") && !fecha.equals("") && !direccion.equals("") && !telefono.equals("") && !correo.equals("") && !programaAcademico.equals("")) {
            String url;
            url = ip + getContext().getString(R.string.ipModificarEstudiante);
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("modifico")) {
                        Log.i("********RESULTADO", "Respuesta server" + response);
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
                    parametros.put("codigoEstudiante", codigo);
                    parametros.put("cedulaEstudiante", cedula);
                    parametros.put("nombreEstudiante", nombre);
                    parametros.put("fechaNacimiento", fecha);
                    parametros.put("estadoEstudiante", estado);
                    parametros.put("direccionEstudiante", direccion);
                    parametros.put("telefonoEstudiante", telefono);
                    parametros.put("correoElectronico", correo);
                    parametros.put("programaAcademico", programaAcademico);
                    parametros.put("semestre_numeroSemetre", semestre);
                    Log.i("--------PARAMETROS ", parametros.toString());
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);
        } else {
            Toast.makeText(getContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }


    }

    private void buscarEstudiante() {
        String url;
        url = ip + getContext().getString(R.string.ipObtenerEstudiante)+"19766";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
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
        estudiante = null;
        JSONArray jsonEstudiante = response.optJSONArray("estudiantes");
        JSONObject jsonObjectEstudiante;
        arrayEstudiante = new ArrayList();
        arrayListEstudiante = new ArrayList();
        //final ArrayList listaNombres = new ArrayList();

        try {
            for (int i = 0; i < jsonEstudiante.length(); i++) {
                jsonObjectEstudiante = jsonEstudiante.getJSONObject(i);
                estudiante = new EstudianteVO();
                estudiante.setCedulaEstudiante(jsonObjectEstudiante.getInt("cedula"));
                estudiante.setNombreEstudiante(jsonObjectEstudiante.getString("nombre"));
                estudiante.setFechaNacimiento(jsonObjectEstudiante.getString("fecha"));
                estudiante.setEstadoEstado(jsonObjectEstudiante.getString("estado"));
                estudiante.setDireccion(jsonObjectEstudiante.getString("direccion"));
                estudiante.setTelefono(jsonObjectEstudiante.getString("telefono"));
                estudiante.setCorreo(jsonObjectEstudiante.getString("correo"));
                estudiante.setPrograma(jsonObjectEstudiante.getInt("programa"));
                estudiante.setSemestre(jsonObjectEstudiante.getInt("semestre"));
                arrayEstudiante.add(estudiante);
            }
            txtCedula.setText(estudiante.getCedulaEstudiante());
            txtNombre.setText(estudiante.getNombreEstudiante());
            txtFechaNacimiento.setText(estudiante.getFechaNacimiento());
            txtEstado.setText(estudiante.getEstadoEstado());
            txtDireccion.setText(estudiante.getDireccion());
            txtTelefono.setText(estudiante.getTelefono());
            txtCorreo.setText(estudiante.getCorreo());
            spnPrograma.setSelection(estudiante.getPrograma());
            spnSemestre.setSelection(estudiante.getSemestre());
        } catch (Exception e) {

        }

        JSONArray jsonPrograma = response.optJSONArray("programa");
        JSONObject jsonObjectPrograma;
        arrayProgramas = new ArrayList();
        try {
            for (int i = 0; i < jsonPrograma.length(); i++) {
                jsonObjectPrograma = jsonPrograma.getJSONObject(i);
                arrayProgramas.add(jsonObjectPrograma.getString("programa"));
            }

            ArrayAdapter<CharSequence> adapterPrograma = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayProgramas);
            spnPrograma.setAdapter(adapterPrograma);
            spnPrograma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    programaAcademico = String.valueOf(position + 1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

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
            spnSemestre.setAdapter(adapterSemestre);
            spnSemestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    semestre = String.valueOf(position + 1);
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
