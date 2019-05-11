package com.example.victor.eam.director_programa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsignarMaterias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsignarMaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignarMaterias extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    ArrayList arrayMaterias, arrayDocentes;
    Spinner spnMateria, spnDocente;
    public AsignarMaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsignarMaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static AsignarMaterias newInstance(String param1, String param2) {
        AsignarMaterias fragment = new AsignarMaterias();
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
        View vista = inflater.inflate(R.layout.fragment_asignar_materias, container, false);
        ip = getContext().getString(R.string.ip);
        spnMateria = vista.findViewById(R.id.spnMateria);
        spnDocente = vista.findViewById(R.id.spnDocente);
        request = Volley.newRequestQueue(getContext());
        cargarDocente();
        cargarMaterias();
        return vista;
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
        JSONArray jsonDocente = response.optJSONArray("docente");
        JSONObject jsonObjectDocente;
        arrayDocentes = new ArrayList();
        try {
            for (int i = 0; i < jsonDocente.length(); i++) {
                jsonObjectDocente = jsonDocente.getJSONObject(i);
                arrayDocentes.add(jsonObjectDocente.getString("docente"));
            }

            ArrayAdapter<CharSequence> adapterDocente = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayDocentes);
            spnDocente.setAdapter(adapterDocente);
            spnDocente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    docente = String.valueOf(position + 1 );
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
