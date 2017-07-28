package com.example.olave.laboratorioandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by prog on 05/07/2017.
 */

public class AdapterClientes extends BaseAdapter{

    protected Activity activity;
    ArrayList<Cliente> clientes;

    public AdapterClientes(Activity activity, ArrayList<Cliente> clientes) {
        this.activity = activity;
        this.clientes = clientes;
    }

    public AdapterClientes() {
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int i) {
        return clientes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    View v2;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        v2 = view;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_socio, null);
        }

        Cliente dir = clientes.get(i);

        TextView nom = (TextView) v.findViewById(R.id.nombre_socio);
        nom.setText("NOMBRE: "+dir.getNombre());

        TextView ced = (TextView) v.findViewById(R.id.cedula_socio);
        ced.setText("CEDULA: "+dir.getCedula());



        ImageView im = (ImageView) v.findViewById(R.id.img_perfil_socio);

        im.setImageBitmap(dir.getImg());

        return v;
    }



}
