package com.example.victor.eam.docente;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
 * {@link RegistrarNotasFallas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrarNotasFallas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarNotasFallas extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    Spinner spinnerCurso;
    ListView listaEstudiantes;
    private Dialog dialogoFallasNotas;

    //Variables
    String ip;
    ArrayList curso;
    String nombreCurso, nombreEstudiante;

    public RegistrarNotasFallas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarNotasFallas.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarNotasFallas newInstance(String param1, String param2) {
        RegistrarNotasFallas fragment = new RegistrarNotasFallas();
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
        View vista = inflater.inflate(R.layout.fragment_registrar_notas_fallas, container, false);
        ip = getContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getContext());
        spinnerCurso = vista.findViewById(R.id.spinnerCurso);
        spinnerCurso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cargarLstEstudiante("123");
            }
        });
        listaEstudiantes = vista.findViewById(R.id.lstEstudianteFN);

        return vista;
    }

    private void cargarLstEstudiante(String idDocente) {
        String url;
        url = ip + getContext().getString(R.string.ipCargarCursoEstudiantes)+idDocente;
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
        JSONArray jsonCurso = response.optJSONArray("estudiantecurso");
        JSONObject jsonObjectCurso;
        curso = new ArrayList();
        try {
            for (int i = 0; i < jsonCurso.length(); i++) {
                jsonObjectCurso = jsonCurso.getJSONObject(i);
                curso.add(jsonObjectCurso.getString("estudiante"));
            }
            ArrayAdapter<String> adapterMateria = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, curso);
            listaEstudiantes.setAdapter(adapterMateria);
            listaEstudiantes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
