package com.example.victor.eam.docente;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.eam.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrearAgenda.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrearAgenda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearAgenda extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnDiaAgenda;
    EditText txtHoraIClase, txtHoraFClase, txtHoraIAsesori, txtHoraFAsesoria;
    Button btnCrearAgenda;
String ip;
    private RequestQueue request;
    private StringRequest stringRequest;
    public CrearAgenda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearAgenda.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearAgenda newInstance(String param1, String param2) {
        CrearAgenda fragment = new CrearAgenda();
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
        View vista = inflater.inflate(R.layout.fragment_crear_agenda, container, false);
        ip = getContext().getString(R.string.ip);
        request = Volley.newRequestQueue(getContext());
        spnDiaAgenda = vista.findViewById(R.id.spnDiaAgenda);
        txtHoraIClase = vista.findViewById(R.id.txtHoraInicioClase);
        txtHoraIClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraIClase();
            }
        });
        txtHoraFClase = vista.findViewById(R.id.txtHoraFinClase);
        txtHoraFClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraFClase();
            }
        });
        txtHoraIAsesori = vista.findViewById(R.id.txtHoraInicioAsesoria);
        txtHoraIAsesori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraIAsesoria();
            }
        });
        txtHoraFAsesoria = vista.findViewById(R.id.txtHoraFinAsesoria);
        txtHoraFAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarHoraFAsesoria();
            }
        });
        btnCrearAgenda = vista.findViewById(R.id.btnCrearAgenda);
        btnCrearAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAgenda();
            }
        });
        configurarDia();
        return vista;
    }

    private void crearAgenda() {
        final String dia = spnDiaAgenda.getSelectedItem().toString();
        final String horaInicioClase = txtHoraIClase.getText().toString();
        final String horaFinClase = txtHoraFClase.getText().toString();
        final String horaInicioAsesoria = txtHoraIAsesori.getText().toString();
        final String horaFinAsesoria = txtHoraFAsesoria.getText().toString();
        final int Docente=1;

        if (!horaInicioClase.equals("")||!horaFinClase.equals("")){
            String url;
            url = ip + getContext().getString(R.string.ipCrearAgendaDocente);
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
                    parametros.put("Dia", dia);
                    parametros.put("HoraInicioClase", horaInicioClase);
                    parametros.put("HoraFinClase", horaFinClase);
                    parametros.put("HoraInicioAsesoria", horaInicioAsesoria);
                    parametros.put("HoraFinAsesoria", horaFinAsesoria);
                    parametros.put("Docente_IdDocente", Docente+"");
                    return parametros;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.add(stringRequest);

        }else {
            Toast.makeText(getContext(), "¡¡ Existen campos vacios !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void configurarDia() {
        String[] dia = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,dia);
        spnDiaAgenda.setAdapter(adapter);
    }

    public void configurarHoraIClase() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtHoraIClase.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }
    public void configurarHoraFClase() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtHoraFClase.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }
    public void configurarHoraIAsesoria() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtHoraIAsesori.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }
    public void configurarHoraFAsesoria() {
        int hora, minutos;
        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtHoraFAsesoria.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
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
