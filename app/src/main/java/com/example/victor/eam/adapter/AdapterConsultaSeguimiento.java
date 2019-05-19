package com.example.victor.eam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.victor.eam.R;
import com.example.victor.eam.entidades.MateriaVO;

import java.util.ArrayList;

public class AdapterConsultaSeguimiento extends BaseAdapter {

    private Context context;
    private ArrayList<MateriaVO> listaMaterias;

    public AdapterConsultaSeguimiento(Context context, ArrayList<MateriaVO> listaMaterias) {
        this.context = context;
        this.listaMaterias = listaMaterias;
    }

    @Override
    public int getCount() {
        return listaMaterias.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMaterias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MateriaVO item = (MateriaVO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.modelo_consultar_seguimiento,null);
        TextView campoMateria,campoNotas,campoFallas;
        campoMateria = convertView.findViewById(R.id.campoMateriaModelo);
        campoNotas = convertView.findViewById(R.id.campoNotaModelo);
        campoFallas = convertView.findViewById(R.id.campoFallasModelo);

        campoMateria.setText("Materia: " + item.getNombre());
        campoNotas.setText("Notas: " + item.getNota());
        campoFallas.setText("Fallas: " + item.getFallas());
        return convertView;
    }
}
