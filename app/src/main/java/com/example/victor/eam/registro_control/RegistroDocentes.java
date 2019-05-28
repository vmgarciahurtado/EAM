package com.example.victor.eam.registro_control;

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
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroDocentes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroDocentes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroDocentes extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spinnerTipoDocente;
    EditText campoNombre,campoCedula;
    String ip,tipoDocente;
    Button btnRegistrar;
    ArrayList arrayTipoDocente;
    private RequestQueue request;
    private StringRequest stringRequest;


    public RegistroDocentes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroDocentes.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroDocentes newInstance(String param1, String param2) {
        RegistroDocentes fragment = new RegistroDocentes();
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
        View vista = inflater.inflate(R.layout.fragment_registro_docentes, container, false);
        ip = getContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getContext());
        campoNombre = vista.findViewById(R.id.campoNombre);
        campoCedula = vista.findViewById(R.id.campoCedula);
        spinnerTipoDocente = vista.findViewById(R.id.spinnerTipoDocente);
        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
        cargarTipoDocente();
        return vista;
    }

    private void cargarTipoDocente() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaTipoDocente);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void registrar() {
        final String nombre = campoNombre.getText().toString();
        final String cedula = campoCedula.getText().toString();

       if (!nombre.equals("") || !cedula.equals("") || !tipoDocente.equals("")){
        String url;
        url = ip + getContext().getString(R.string.ipRegistroDocentes);
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("registra")) {
                //    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                } else {
                //    Toast.makeText(getContext(), "response: " + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //    Toast.makeText(getContext(), "Error response: " + error, Toast.LENGTH_SHORT).show();
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
    }

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
        JSONArray jsonFacultad = response.optJSONArray("tipo");
        JSONObject jsonObjectFacultad;
        arrayTipoDocente = new ArrayList();
        try {
            for (int i = 0; i < jsonFacultad.length(); i++) {
                jsonObjectFacultad = jsonFacultad.getJSONObject(i);
                arrayTipoDocente.add(jsonObjectFacultad.getString("tipo"));
            }

            ArrayAdapter<CharSequence> adapterFacultad = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayTipoDocente);
            spinnerTipoDocente.setAdapter(adapterFacultad);
            spinnerTipoDocente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tipoDocente = String.valueOf(position + 1 );
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
