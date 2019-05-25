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

public class AdapterConsultaMaterias extends BaseAdapter {

    private Context context;
    private ArrayList<MateriaVO> listaMaterias;

    public AdapterConsultaMaterias(Context context, ArrayList<MateriaVO> listaMaterias) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.modelo_consultar_materias_estudiante,null);
        TextView campoMateria;
        campoMateria = convertView.findViewById(R.id.campoMateriaModelo);
        campoMateria.setText("Materia: " + item.getNombre());

        return convertView;
    }
}
