package com.example.victor.eam.registro_control;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.victor.eam.entidades.DocenteVO;
import com.example.victor.eam.entidades.EstudianteVO;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarDocente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarDocente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarDocente extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    JsonObjectRequest jsonObjectRequest;

    TextView campoNombre,campoTipo,campoCodigo;
    Spinner spinnerTipoDocente;
    Button btnInactivarDocente;
    LinearLayout linearLayout;

    private DocenteVO docenteVO;
    ArrayList<DocenteVO>arrayDocentes;

    //VARIABLES
    String ip ;
    public ModificarDocente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarDocente.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarDocente newInstance(String param1, String param2) {
        ModificarDocente fragment = new ModificarDocente();
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
        View vista = inflater.inflate(R.layout.fragment_modificar_docente, container, false);
        request = Volley.newRequestQueue(getContext());
        ip = getContext().getString(R.string.ip);
        campoCodigo = vista.findViewById(R.id.campoCodigo);
        campoNombre = vista.findViewById(R.id.campoNombre);
        campoTipo = vista.findViewById(R.id.campoTipoDocente);
        linearLayout = vista.findViewById(R.id.layoutModDocente);
        btnInactivarDocente = vista.findViewById(R.id.btnInactivarDocente);
        btnInactivarDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inactivarDocente();
            }
        });

        cargarDatos();
        return vista;
    }

    private void inactivarDocente() {
        final String codigo = campoCodigo.getText().toString();
        final String estado = "0";

        String url;
        url = ip + getContext().getString(R.string.ipInactivarDocente);
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
                parametros.put("iddocente", codigo);
                parametros.put("estadodocente",estado);
                Log.i("--------PARAMETROS ", parametros.toString());
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);

    }

    private void cargarDatos() {
        String url;
        int codigo = 1024;
        url = ip + getContext().getString(R.string.ipObtenerDocente)+codigo;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
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
        docenteVO = null;
        JSONArray jsonDocente = response.optJSONArray("docentes");
        JSONObject jsonObjectDocente;
        arrayDocentes = new ArrayList();

        try {
            for (int i = 0; i < jsonDocente.length(); i++) {
                jsonObjectDocente = jsonDocente.getJSONObject(i);
                docenteVO = new DocenteVO();
                docenteVO.setId(jsonObjectDocente.getString("iddocente"));
                docenteVO.setNombre(jsonObjectDocente.getString("nombre"));
                docenteVO.setTipo(jsonObjectDocente.getString("tipo"));
                docenteVO.setEstado(jsonObjectDocente.getString("estado"));
                arrayDocentes.add(docenteVO);
                llenarCampos(docenteVO);
            }

        } catch (Exception e) {

        }

    }

    private void llenarCampos(DocenteVO docenteVO) {
        Toast.makeText(getContext(), "DocenteVo" + docenteVO, Toast.LENGTH_SHORT).show();
        campoTipo.setText(docenteVO.getTipo());
        campoNombre.setText(docenteVO.getNombre());
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
