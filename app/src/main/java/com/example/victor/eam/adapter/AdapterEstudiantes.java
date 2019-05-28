package com.example.victor.eam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.victor.eam.R;
import com.example.victor.eam.entidades.EstudianteVO;
import com.example.victor.eam.entidades.MateriaVO;

import java.util.ArrayList;

public class AdapterEstudiantes extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private ArrayList<EstudianteVO> estudiantesVo;
    private View.OnClickListener listener;

    public AdapterEstudiantes(Context context, ArrayList<EstudianteVO> listaEstudiantes) {
        this.context = context;
        this.estudiantesVo = listaEstudiantes;
    }

    @Override
    public int getCount() {
        return estudiantesVo.size();
    }

    @Override
    public Object getItem(int position) {
        return estudiantesVo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EstudianteVO item = (EstudianteVO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.modelo_consultar_materias_estudiante,null);
        TextView campoMateria;
        campoMateria = convertView.findViewById(R.id.campoMateriaModelo);
        campoMateria.setText("Nombre: " + item.getNombreEstudiante());

        return convertView;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
