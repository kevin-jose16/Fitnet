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
 * Created by KevinQ on 13/7/2017.
 */

public class AdapterUsuario extends BaseAdapter{
    ArrayList<Usuario> Usuarios;
    Activity activity;

    public AdapterUsuario(ArrayList<Usuario> usuarios, Activity activity) {
        Usuarios = usuarios;
        this.activity = activity;
    }

    public AdapterUsuario() {
    }

    @Override
    public int getCount() {
        return Usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return Usuarios.get(position);
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
            v = inf.inflate(R.layout.item_usuarios, null);
        }

        Usuario dir = Usuarios.get(position);

        TextView title = (TextView) v.findViewById(R.id.Nombre_User);
        title.setText(dir.getNombre());

        TextView nick = (TextView) v.findViewById(R.id.Nick_User);
        nick.setText(dir.getUsername());

        ImagenRedonda img= (ImagenRedonda) v.findViewById(R.id.imagen_User);
        img.setImageBitmap(dir.getImg());


        return v;
    }



    }

