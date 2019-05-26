package com.example.victor.eam.estudiante;

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
import android.widget.ListView;
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
import com.example.victor.eam.adapter.AdapterConsultaMaterias;
import com.example.victor.eam.entidades.MateriaVO;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroMaterias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroMaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroMaterias extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    String ip, materia, nombreMateria;
    ListView lstMaterias;
    Spinner spnMaterias;
    Button btnagregarLista, btnregistrar;
    MateriaVO materiaVO;
    ArrayList<MateriaVO> arrayMateria;
    ArrayList lista = new ArrayList();
    ArrayList agregarLista = new ArrayList();
    AdapterConsultaMaterias adapterConsultaMaterias;

    public RegistroMaterias() {
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
    public static RegistroMaterias newInstance(String param1, String param2) {
        RegistroMaterias fragment = new RegistroMaterias();
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
        View vista = inflater.inflate(R.layout.fragment_registro_materias, container, false);
        request = Volley.newRequestQueue(getContext());
        ip = getContext().getString(R.string.ip);
        lstMaterias = vista.findViewById(R.id.lstMaterias);
        spnMaterias = vista.findViewById(R.id.spnMateriaEstudiante);
        btnagregarLista = vista.findViewById(R.id.btnAgregarALista);
        btnagregarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLista();
                if (agregarLista.contains(nombreMateria)) {
                    Toast.makeText(getContext(), "Esta materia ya se encuentra agregada", Toast.LENGTH_SHORT).show();
                } else {
                    lista.add(materia);
                    agregarLista.add(nombreMateria);
                }
            }

        });
        btnregistrar = vista.findViewById(R.id.btnRegistrarMaterias);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarMateria();
            }
        });
        cargarMaterias();
        return vista;
    }

    private void cargarMaterias() {
        String url;
        url = ip + getContext().getString(R.string.ipCargarMateriasEstudiante) + "19766";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void registrarMateria() {
        final String codigoEstudiante = "19766";
        if (lista.size() > 0) {
            String url;
            url = ip + getContext().getString(R.string.ipRegistroMateriasEstudiante);
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("Registra")) {
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
                    for (int i = 0; i < lista.size(); i++) {
                        String materia = lista.get(i) + "";
                        parametros.put("Materia_IdMateria", materia);
                        parametros.put("Estudiante_CodigoEstudiante", codigoEstudiante);

                    }
                    return parametros;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);

        } else {
            Toast.makeText(getContext(), "No hay materias agregadas", Toast.LENGTH_SHORT).show();
        }

    }

    private void agregarLista() {
        ArrayAdapter<String> adapterMateria = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, agregarLista);
        lstMaterias.setAdapter(adapterMateria);
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
        materiaVO = null;
        JSONArray json = response.optJSONArray("materias");
        JSONObject jsonObject = null;
        arrayMateria = new ArrayList<>();

        try {

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                materiaVO = new MateriaVO();
                materiaVO.setNombreMateria(jsonObject.getString("nombremateria"));
                materiaVO.setCodigo(jsonObject.getString("idmateria"));
                arrayMateria.add(materiaVO);
            }

            ArrayAdapter<CharSequence> adapterMateria = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayMateria);
            spnMaterias.setAdapter(adapterMateria);
            spnMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    materia = arrayMateria.get(spnMaterias.getPositionForView(view)).getCodigo();
                    nombreMateria = arrayMateria.get(spnMaterias.getPositionForView(view)).getNombreMateria();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" + " " + response, Toast.LENGTH_LONG).show();
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
