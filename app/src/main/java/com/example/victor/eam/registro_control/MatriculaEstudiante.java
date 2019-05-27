package com.example.victor.eam.registro_control;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.example.victor.eam.adapter.AdapterConsultaMaterias;
import com.example.victor.eam.entidades.MateriaVO;
import com.example.victor.eam.entidades.MatriculaVo;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatriculaEstudiante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatriculaEstudiante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatriculaEstudiante extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String ip;

    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    Spinner spinnerTipo;

    ArrayList arrayTipo;

    Button btnAceptar,btnBuscar;
    TextView txtValorPagar,txtPrograma;
    EditText campoCodigo,campoCuotas;
    CheckBox radioCredito,radioContado;

    String costo,programa;

    public MatriculaEstudiante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatriculaEstudiante.
     */
    // TODO: Rename and change types and number of parameters
    public static MatriculaEstudiante newInstance(String param1, String param2) {
        MatriculaEstudiante fragment = new MatriculaEstudiante();
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
        View vista =  inflater.inflate(R.layout.fragment_matricula_estudiante, container, false);
        request = Volley.newRequestQueue(getContext());
        ip = getContext().getString(R.string.ip);

        campoCodigo = vista.findViewById(R.id.campoCodigoMatricula);
        campoCuotas = vista.findViewById(R.id.campoCantidadCuotas);

        btnAceptar = vista.findViewById(R.id.btnAcepterMatricula);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matricular();
            }
        });
        btnBuscar  = vista.findViewById(R.id.btnBuscarMatricula);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEstudiante();
            }
        });

        txtValorPagar = vista.findViewById(R.id.txtValorPagar);
        txtPrograma = vista.findViewById(R.id.txtPrograma);
        arrayTipo = new ArrayList();
        arrayTipo.add("Contado");
        arrayTipo.add("Credido");

        spinnerTipo = vista.findViewById(R.id.spinnerTipo);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayTipo);
        spinnerTipo.setAdapter(adapter);
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            campoCuotas.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            campoCuotas.setVisibility(View.VISIBLE);
                            break;
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return vista;
    }




    private void buscarEstudiante() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaEstudiantePrograma)+"192584";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void matricular() {
/*        final String nombre = campoNombre.getText().toString();
        final String cedula = campoCedula.getText().toString();


        if (!nombre.equals("") || !cedula.equals("") || !tipoDocente.equals("")){
            String url;
            url = ip + getContext().getString(R.string.ipRegistroDocentes);
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
                    parametros.put("iddocente", cedula);
                    parametros.put("nombredocente", nombre);
                    parametros.put("tipodocente", tipoDocente);
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);

        }else {
            Toast.makeText(getContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }*/
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
        JSONArray json = response.optJSONArray("matricula");
        JSONObject jsonObject = null;
        try {

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                costo = jsonObject.getString("costo");
                programa = jsonObject.getString("nombre");
            }

            txtValorPagar.setText("Valor a pagar: " + costo);
            txtPrograma.setText("Programa: " + programa);

        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" + " " + response, Toast.LENGTH_LONG).show();
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
