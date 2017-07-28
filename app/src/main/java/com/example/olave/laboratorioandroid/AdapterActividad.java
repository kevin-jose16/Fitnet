package com.example.olave.laboratorioandroid;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KevinQ on 13/7/2017.
 */

public class AdapterActividad  extends BaseAdapter{
    ArrayList<Actividad> Actividades;
    Activity activity;

    public AdapterActividad() {
    }

    public AdapterActividad(ArrayList<Actividad> actividades, Activity activity) {
        Actividades = actividades;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Actividades.size();
    }

    @Override
    public Object getItem(int position) {
        return Actividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_actividad, null);
        }

        Actividad dir = Actividades.get(position);

        TextView title = (TextView) v.findViewById(R.id.Nombre_act);
        title.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.Precio_act);
        description.setText("Descripcion: "+dir.getPrecio());

        TextView Dias = (TextView) v.findViewById(R.id.Dias_act);
        Dias.setText("Dias: "+String.valueOf(dir.getDias()));

        TextView periodo = (TextView) v.findViewById(R.id.Periodo);
        periodo.setText("Periodo: "+String.valueOf(dir.getPeriodo()));


        return v;
    }
    }







