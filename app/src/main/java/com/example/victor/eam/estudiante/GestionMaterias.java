package com.example.victor.eam.estudiante;

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
import android.widget.CheckBox;
import android.widget.ListView;
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
import com.example.victor.eam.entidades.DetalleMateriaVo;
import com.example.victor.eam.entidades.MateriaVO;
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
 * {@link GestionMaterias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GestionMaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionMaterias extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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

    Spinner spinnerHorario;

    ListView lstMaterias;
    String ip, codigo;

    String nota,nombre,horario,fallas;

    Dialog dialogDatos;

    AdapterConsultaMaterias adapterConsultaMaterias;

    ArrayList<MateriaVO> listaMaterias;
    ArrayList<DetalleMateriaVo> listaDetalle;
    ArrayList arrayHorario;

    MateriaVO materiaVO;
    DetalleMateriaVo detalleMateriaVo;

    public GestionMaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GestionMaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static GestionMaterias newInstance(String param1, String param2) {
        GestionMaterias fragment = new GestionMaterias();
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
        View vista = inflater.inflate(R.layout.fragment_gestion_materias, container, false);
        request = Volley.newRequestQueue(getContext());
        ip = getContext().getString(R.string.ip);
        lstMaterias = vista.findViewById(R.id.lstMateriasGestion);
        dialogDatos = new Dialog(getContext());
        cargarMaterias();
        return vista;
    }

    private void cargarMaterias() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultarMateriaEstudiante);
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
//=====================================================================================================================
        materiaVO = null;
        JSONArray json = response.optJSONArray("materia");
        JSONObject jsonObject = null;
        listaMaterias = new ArrayList<>();

        try {

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                materiaVO = new MateriaVO();
                materiaVO.setNombreMateria(jsonObject.getString("nombre"));
                materiaVO.setCodigo(jsonObject.getString("id"));
                materiaVO.setNombreDocente(jsonObject.getString("docente"));
                listaMaterias.add(materiaVO);
            }

            adapterConsultaMaterias = new AdapterConsultaMaterias(getContext(), listaMaterias);
            lstMaterias.setAdapter(adapterConsultaMaterias);
            lstMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    codigo = listaMaterias.get(lstMaterias.getPositionForView(view)).getCodigo();
                    Toast.makeText(getContext(), "Codigo curso: " + codigo, Toast.LENGTH_SHORT).show();
                    cargarDatosMateria(codigo);
                    showPopup();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" + " " + response, Toast.LENGTH_LONG).show();
        }

//=====================================================================================================================
        detalleMateriaVo = null;
        JSONArray jsonDetalle = response.optJSONArray("detalle");
        JSONObject jsonObjectDetalle = null;
        listaDetalle = new ArrayList<>();
        arrayHorario = new ArrayList();

        try {

            for (int i = 0; i < jsonDetalle.length(); i++) {
                jsonObjectDetalle = jsonDetalle.getJSONObject(i);
                detalleMateriaVo = new DetalleMateriaVo();
                detalleMateriaVo.setNombre(jsonObjectDetalle.getString("nombre"));
                detalleMateriaVo.setNota(jsonObjectDetalle.getString("nota"));
                arrayHorario.add(jsonObjectDetalle.getString("horaio"));
                //detalleMateriaVo.setHorario(jsonObjectDetalle.getString("horaio"));
                detalleMateriaVo.setFallas(jsonObjectDetalle.getString("fallas"));
                listaDetalle.add(detalleMateriaVo);
            }

            nombre = detalleMateriaVo.getNombre();
            nota = detalleMateriaVo.getNota();
            //horario = detalleMateriaVo.getHorario();
            fallas = detalleMateriaVo.getFallas();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item, arrayHorario);
            spinnerHorario.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" + " " + response, Toast.LENGTH_LONG).show();
        }
//=========================================================================================================================
    }


    private void showPopup() {
        TextView txtFallas,txtNota;
        final CheckBox checkBoxCancelarMateria;
        Button aceptar;

        try {
            dialogDatos.setContentView(R.layout.popup_materias);
            Objects.requireNonNull(dialogDatos.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogDatos.show();
        } catch (Exception e) {
            Log.i("Error ", e.toString());
        }

        checkBoxCancelarMateria = dialogDatos.findViewById(R.id.checkboxPopup);
        spinnerHorario = dialogDatos.findViewById(R.id.spinnerHorarioPopup);
        txtFallas = dialogDatos.findViewById(R.id.txtFallasPopup);
        txtNota = dialogDatos.findViewById(R.id.txtNotasPopup);

        txtFallas.setText(fallas);
        txtNota.setText(nota);

        aceptar = dialogDatos.findViewById(R.id.btnAceptarPopup);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkBoxCancelarMateria.isChecked()){
                   cancelarMateria();
               }else {
                   dialogDatos.hide();
               }
            }
        });
    }

    private void cancelarMateria() {
        String url = ip + getContext().getString(R.string.ipCancelarMateria);
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
                parametros.put("idmateria", codigo);
                parametros.put("codigo", "12345");
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);

    }

    private void cargarDatosMateria(String codigo) {
        String url;
        url = ip + getContext().getString(R.string.ipConsultarDatosMateria);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
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
