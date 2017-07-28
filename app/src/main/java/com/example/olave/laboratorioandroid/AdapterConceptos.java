package com.example.olave.laboratorioandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KevinQ on 5/7/2017.
 */

public class AdapterConceptos extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Concepto> Conceptos;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Concepto> getConceptos() {
        return Conceptos;
    }

    public void setConceptos(ArrayList<Concepto> conceptos) {
        Conceptos = conceptos;
    }

    @Override
    public int getCount() {
        return Conceptos.size();
    }

    @Override
    public Object getItem(int position) {
        return Conceptos.get(position);
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
            v = inf.inflate(R.layout .item_concepto, null);
        }

        Concepto dir = Conceptos.get(position);
        TextView title = (TextView) v.findViewById(R.id.Nombre_Con);
        title.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.Descripcion_Con);
        description.setText(dir.getDescripcion());

        return v;
    }


    public AdapterConceptos(Activity activity, ArrayList<Concepto> conceptos) {
        this.activity = activity;
        Conceptos = conceptos;
    }
}
