package com.example.victor.eam.registro_control;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victor.eam.R;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

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
    EditText txtCodigo, txtCedula, txtNombre, txtFechaNacimiento, txtEstado, txtDireccion, txtTelefono, txtCorreo;
    Spinner spnPrograma, spnSemestre;
    Button btnBuscar, btnModificar, btnInactivar;
    String ip, programaAcademico, semestre;
    ArrayList arrayProgramas;
    ArrayList arraySemestres;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        btnBuscar = vista.findViewById(R.id);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEstudiante();
            }
        });
        btnModificar = vista.findViewById(R.id.);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEstudiante();
            }
        });
        btnInactivar = vista.findViewById(R.id.);
        btnInactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inactivarEstudiante();
            }
        });
        cargarPrograma();
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

    }

    private void modificarEstudiante() {

    }

    private void buscarEstudiante() {

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
                    semestre = String.valueOf(position+1);
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
