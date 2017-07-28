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
 * Created by KevinQ on 2/7/2017.
 */

public class AdapterArticulos extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Articulo> Articulos;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Articulo> getArticulos() {
        return Articulos;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        Articulos = articulos;
    }

    public AdapterArticulos() {
    }

    public AdapterArticulos(Activity activity, ArrayList<Articulo> articulos) {
        this.activity = activity;
        Articulos = articulos;
    }

    @Override
    public int getCount() {
     return    Articulos.size();
    }

    @Override
    public Object getItem(int position) {
        return Articulos.get(position);
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
            v = inf.inflate(R.layout.item_articulo, null);
        }

        Articulo dir = Articulos.get(position);

        TextView title = (TextView) v.findViewById(R.id.NombreArt);
        title.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.PrecioArt);
        description.setText(dir.getPrecio());

        return v;
    }
}
