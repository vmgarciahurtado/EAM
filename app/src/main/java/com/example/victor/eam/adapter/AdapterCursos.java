package com.example.victor.eam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.victor.eam.R;
import com.example.victor.eam.entidades.CursoVO;

import java.util.ArrayList;

public class AdapterCursos extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private ArrayList<CursoVO>listaCursos;

    public AdapterCursos(Context context, ArrayList<CursoVO> listaCursos) {
        this.context = context;
        this.listaCursos = listaCursos;
    }

    @Override
    public int getCount() {
        return listaCursos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CursoVO item = (CursoVO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.modelo_adapter_cursos,null);
        TextView campoNombreCurso;

        campoNombreCurso = convertView.findViewById(R.id.campoCursoModelo);
        campoNombreCurso.setText(item.getNombre());
        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
