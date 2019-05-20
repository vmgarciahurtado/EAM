package com.example.victor.eam.docente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.eam.R;
import com.example.victor.eam.adapter.AdapterCursos;
import com.example.victor.eam.entidades.CursoVO;
import com.example.victor.eam.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerCursos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerCursos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerCursos extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ListView listViewCursos;
    AdapterCursos adapterCursos;
    ArrayList<CursoVO>listaCursos;
    CursoVO cursoVO;
    //VARIABLES
    String ip;
    public VerCursos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerCursos.
     */
    // TODO: Rename and change types and number of parameters
    public static VerCursos newInstance(String param1, String param2) {
        VerCursos fragment = new VerCursos();
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
        View vista = inflater.inflate(R.layout.fragment_ver_cursos, container, false);
        ip = getContext().getString(R.string.ip);
        listViewCursos = vista.findViewById(R.id.lstCursos);
        request = Volley.newRequestQueue(getContext());
        cargarCursos();
        return vista;
    }

    private void cargarCursos() {
        String url;
        url = ip + getContext().getString(R.string.ipConsultaCursos);
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

        cursoVO = null;
        JSONArray json = response.optJSONArray("cursos");
        JSONObject jsonObject = null;
        listaCursos = new ArrayList<>();

        try {

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                cursoVO = new CursoVO();
                cursoVO.setNombre(jsonObject.getString("nombreCurso"));
                cursoVO.setCodigo(jsonObject.getString("idcurso"));
                listaCursos.add(cursoVO);
            }
            //setRetroMala(preguntas.getRetromala());


            adapterCursos = new AdapterCursos(getContext(),listaCursos);
            listViewCursos.setAdapter(adapterCursos);
            listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String codigo = listaCursos.get(listViewCursos.getPositionForView(view)).getCodigo();
                    Toast.makeText(getContext(), "Codigo curso: " + codigo, Toast.LENGTH_SHORT).show();
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
