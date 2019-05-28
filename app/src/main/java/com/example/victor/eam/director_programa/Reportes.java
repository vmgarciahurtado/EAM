package com.example.victor.eam.director_programa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.victor.eam.R;
import com.example.victor.eam.docente.FragmentReporteCursos;
import com.example.victor.eam.docente.ReporteGanadores;
import com.example.victor.eam.docente.ReporteNotasCurso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reportes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reportes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reportes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button btnMortalidad,btnInacistencias;

    public Reportes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reportes.
     */
    // TODO: Rename and change types and number of parameters
    public static Reportes newInstance(String param1, String param2) {
        Reportes fragment = new Reportes();
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
        View vista = inflater.inflate(R.layout.fragment_reportes, container, false);
        btnMortalidad = vista.findViewById(R.id.btnMortalidad);
        btnMortalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragment = new ReporteMortalidad();
                getFragmentManager().beginTransaction().replace(R.id.containerDirectorPrograma, miFragment).commit();
            }
        });

        btnInacistencias = vista.findViewById(R.id.btnInacistencias);
        btnInacistencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment miFragment = new ReporteInacistencias();
                getFragmentManager().beginTransaction().replace(R.id.containerDirectorPrograma, miFragment).commit();
            }
        });

        return vista;

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
