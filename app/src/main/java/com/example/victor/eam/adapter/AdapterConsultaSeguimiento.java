package com.example.victor.eam.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
