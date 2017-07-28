package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.category;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fm_articulos extends Fragment {
    ImageButton agregar, search;
    ArrayList<Articulo> art = new ArrayList<Articulo>();
    ArrayList<Articulo> busqeda = new ArrayList<Articulo>();
    EditText texto;
    Fragment fragment=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new Clase1().execute();
        View rootView = inflater.inflate(R.layout.fm_articulos, container, false);
        agregar = (ImageButton) rootView.findViewById(R.id.Agregar);
        search = (ImageButton) rootView.findViewById(R.id.Search);
        texto = (EditText) rootView.findViewById(R.id.Texto);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=texto.getText().toString();
                busqeda.removeAll(busqeda);
                for(int i=0;i<art.size();i++)
                {
                    String artic=art.get(i).getNombre();
                    if(artic.contains(nombre)){
                        busqeda.add(art.get(i));


                    }




                }
                ListView lv = (ListView) getView().findViewById(R.id.Articulos);

                AdapterArticulos adapter = new AdapterArticulos(getActivity(),busqeda);

                lv.setAdapter(adapter);

            }

        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment =new Fm_agregarArticulos();

                FragmentManager fragmentManager = getFragmentManager();
              fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,fragment).addToBackStack("lista").commit();

            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public class Clase1 extends AsyncTask<String, Void, String> {
        String res;

        @Override
        protected String doInBackground(String... params) {
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "sesion", Context.MODE_PRIVATE);
            String user = sharedPref.getString("username", null);
            String password = sharedPref.getString("password", null);
            String token = KeyGenerator.getSecurePassword(user, password);
            try {
                String KEY = token;
                URL url = new URL("http://fitnet.com.uy/api/articulos/listar/" + KEY);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                res = response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray json = new JSONArray(result);
                    art.removeAll(art);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject Artj = (JSONObject) json.get(i);
                        Articulo a = new Articulo(Artj.get("nombre").toString(), Artj.get("precio").toString());
                        art.add(a);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(Fm_articulos.this.getActivity(), "Tu nombre de usuario o la contraseña son incorrectos",
                        Toast.LENGTH_LONG).show();


            }
            ListView lv = (ListView) getView().findViewById(R.id.Articulos);

            AdapterArticulos adapter = new AdapterArticulos(getActivity(), art);

            lv.setAdapter(adapter);

        }

    }
}