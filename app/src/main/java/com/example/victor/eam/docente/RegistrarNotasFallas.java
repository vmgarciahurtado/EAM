package com.example.victor.eam.docente;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.victor.eam.adapter.AdapterCursos;
import com.example.victor.eam.adapter.AdapterEstudiantes;
import com.example.victor.eam.entidades.CursoVO;
import com.example.victor.eam.entidades.EstudianteVO;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    Spinner spinnerCurso, spinnerCorte;
    ListView listaEstudiantes;
    private Dialog dialogoFallasNotas;
    AdapterEstudiantes adapterEstudiantes;

    //Variables
    String ip, corte, cursoid, estudiante, docente;
    ArrayList <EstudianteVO> estudiantes;
    ArrayList <CursoVO> cursoDocente;
    String nombreCurso, nombreEstudiante;
    CursoVO cursoVO;
    EstudianteVO estudianteVO;

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
        dialogoFallasNotas = new Dialog(this.getContext());
        spinnerCurso = vista.findViewById(R.id.spinnerCurso);
        docente = "123";
        cargarSpinnerCurso(docente);
        spinnerCurso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cargarLstEstudiante("123");
            }
        });
        listaEstudiantes = vista.findViewById(R.id.lstEstudianteFN);
        return vista;
    }

    private void cargarSpinnerCurso(String docente) {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaCursos) + docente;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarLstEstudiante(String idDocente) {
        String url;
        url = ip + getContext().getString(R.string.ipCargarCursoEstudiantes) + idDocente;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void showPopupNotasFallas() {
        Button registrar, cancelar;
        final EditText txtNotas, txtFallas;

        try {
            dialogoFallasNotas.setContentView(R.layout.popup_notas_fallas);
            Objects.requireNonNull(dialogoFallasNotas.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogoFallasNotas.show();
        } catch (Exception e) {
            Log.i("Error ", e.toString());
        }
        txtNotas = dialogoFallasNotas.findViewById(R.id.txtNotasCursoPopup);
        txtFallas = dialogoFallasNotas.findViewById(R.id.txtFallasCursoPopup);
        spinnerCorte = dialogoFallasNotas.findViewById(R.id.spinnerCortePopup);
        spinnerCorte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                corte = String.valueOf(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String notas = txtNotas.getText().toString();
        final String fallas = txtFallas.getText().toString();
        registrar = dialogoFallasNotas.findViewById(R.id.btnRegistrarNotasFallasPopup);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarNotasFallas(estudiante, cursoid, fallas, corte, notas);
                dialogoFallasNotas.hide();
            }
        });

        cancelar = dialogoFallasNotas.findViewById(R.id.btnCancelarNotasFallasPopup);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoFallasNotas.hide();
                Toast.makeText(getContext(), " Accion cancelada ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarNotasFallas(final String estudiante, final String curso, final String fallas, final String corte, final String notas) {
        String url;
        url = ip + getContext().getString(R.string.ipRegistrarNotasFallas);
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
                parametros.put("definitivacorte", String.valueOf(notas));
                parametros.put("corte", String.valueOf(corte));
                parametros.put("fallas", String.valueOf(fallas));
                parametros.put("codigoestudiante", String.valueOf(estudiante));
                parametros.put("idcurso", curso);
                Log.i("--------PARAMETROS ", parametros.toString());
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);
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
        estudianteVO=null;
        JSONArray jsonCurso = response.optJSONArray("estudiantecurso");
        JSONObject jsonObjectCurso;
        estudiantes = new ArrayList();
        try {
            for (int i = 0; i < jsonCurso.length(); i++) {
                jsonObjectCurso = jsonCurso.getJSONObject(i);
                estudianteVO = new EstudianteVO();
                estudianteVO.setCodigoEstudiante(jsonCurso.getInt(Integer.parseInt("codigoestudiante")));
                estudianteVO.setNombreEstudiante(jsonCurso.getString(Integer.parseInt("estudiante")));
                estudiantes.add(estudianteVO);
            }
            adapterEstudiantes=new AdapterEstudiantes(getContext(), estudiantes);
            listaEstudiantes.setAdapter(adapterEstudiantes);
            listaEstudiantes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } catch (Exception e) {

        }
        ///////////////////////////

        cursoVO = null;
        JSONArray json = response.optJSONArray("cursos");
        JSONObject jsonObject = null;
        cursoDocente = new ArrayList<>();

        try {

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                cursoVO = new CursoVO();
                cursoVO.setNombre(jsonObject.getString("nombreCurso"));
                cursoVO.setCodigo(jsonObject.getString("idcurso"));
                cursoDocente.add(cursoVO);
            }
            ArrayAdapter<CharSequence> adapterCuDocente = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, cursoDocente);
            spinnerCurso.setAdapter(adapterCuDocente);
            spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cursoid = cursoDocente.get(spinnerCurso.getPositionForView(view)).getCodigo();
                    Toast.makeText(getContext(), "Codigo curso: " + cursoid, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
