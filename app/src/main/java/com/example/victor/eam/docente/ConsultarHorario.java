package com.example.victor.eam.docente;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.eam.R;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultarHorario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultarHorario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarHorario extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    Spinner spnDiaFiltrar;
    ListView lstHorarioDocente;
    String docente, ip;
    ArrayList lstHorario;
    String credenciales;

    public ConsultarHorario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarHorario.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarHorario newInstance(String param1, String param2) {
        ConsultarHorario fragment = new ConsultarHorario();
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
        View vista = inflater.inflate(R.layout.fragment_consultar_horario_docente, container, false);
        ip = getContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getContext());
        spnDiaFiltrar = vista.findViewById(R.id.spnDiaHorarioDocente);
        lstHorarioDocente = vista.findViewById(R.id.lstHorarioDocente);
        SharedPreferences preferences = Objects.requireNonNull(this).getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        credenciales = preferences.getString("credenciales", "No existe el valor");
        configurarDia();
        return vista;
    }

    public void configurarDia() {
        String[] dias = {"Lunes", "Martes","Miercoles", "Jueves", "Viernes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, dias);
        spnDiaFiltrar.setAdapter(adapter);
        spnDiaFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String diahpta= spnDiaFiltrar.getSelectedItem().toString();
                cargarLista(diahpta);
                //Toast.makeText(getContext(), "Este es el dia: "+diahpta, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void cargarLista(String dia) {
        String url;
        url = ip + getContext().getString(R.string.ipConsultarHorarioDocente) + credenciales + "&dia=" + dia;
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
        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
        JSONArray json = response.optJSONArray("horariodocente");
        JSONObject jsonObject;
        lstHorario = new ArrayList<>();

        try {
            if (json.length() > 0) {
                for (int i = 0; i < json.length(); i++) {
                    jsonObject = json.getJSONObject(i);
                    lstHorario.add(jsonObject.getString("horario"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lstHorario);
                lstHorarioDocente.setAdapter(adapter);
            } else {
                lstHorario.add("No hay registros");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lstHorario);
                lstHorarioDocente.setAdapter(adapter);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" + " " + response, Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
