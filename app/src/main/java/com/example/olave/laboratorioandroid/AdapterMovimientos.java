package com.example.olave.laboratorioandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olave on 9/7/2017.
 */

public class AdapterMovimientos extends BaseAdapter {

    protected Activity activity;

    protected ArrayList<Movimiento> Movimientos;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movimiento> getConceptos() {
        return Movimientos;
    }

    public void setConceptos(ArrayList<Movimiento> movimientos) {
        Movimientos = movimientos;
    }

    @Override
    public int getCount() {
        return Movimientos.size();
    }

    @Override
    public Object getItem(int i) {
        return Movimientos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout .item_movimiento, null);
        }

        Movimiento dir = Movimientos.get(i);

        TextView concepto = (TextView) v.findViewById(R.id.concepto_mov);
        concepto.setText(dir.getConcepto());


        TextView description = (TextView) v.findViewById(R.id.descripcion_mov);
        description.setText(dir.getDescripcion());

        TextView cost = (TextView) v.findViewById(R.id.importe_mov);
        cost.setText(String.valueOf(dir.getImporte()));



        return v;
    }
    public AdapterMovimientos(Activity activity, ArrayList<Movimiento> movimientos) {
        this.activity = activity;
        Movimientos = movimientos;
    }

}
