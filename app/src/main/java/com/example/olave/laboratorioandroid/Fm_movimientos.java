package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Fm_movimientos extends Fragment {
    ImageButton agregar, search;
    ArrayList<Movimiento> movimientos = new ArrayList<>();
    ArrayList<Movimiento> busqeda = new ArrayList<>();
    EditText texto;
    Fragment fragment=null;
    ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        new Fm_movimientos.Clase1().execute();

        View rootView = inflater.inflate(R.layout.fm_movimientos, container, false);
        lista= (ListView) rootView.findViewById(R.id.movimientos);

        agregar= (ImageButton) rootView.findViewById(R.id.ADD_Mov);
        search= (ImageButton) rootView.findViewById(R.id.BT_Busqueda_Mov);
        texto= (EditText) rootView.findViewById(R.id.Busqueda_Mov);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc=texto.getText().toString();
                busqeda.removeAll(busqeda);
                for(int i=0;i<movimientos.size();i++)
                {
                    String mov=movimientos.get(i).getDescripcion();
                    if(mov.contains(desc)){
                        busqeda.add(movimientos.get(i));


                    }




                }
                ListView  lv = (ListView) getView().findViewById(R.id.movimientos);

                AdapterMovimientos adapter = new AdapterMovimientos(getActivity(),busqeda);

                lv.setAdapter(adapter);

            }

        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment =new Fm_agregarMovimiento();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,fragment).addToBackStack("lista").commit();

            }
        });
        return rootView;
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
                URL url = new URL("http://fitnet.com.uy/api/movimientos/listar/" + KEY);
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
                    movimientos.removeAll(movimientos);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject Artj = (JSONObject) json.get(i);
                        JSONObject conc = (JSONObject) Artj.get("concepto");
                        Movimiento a = new Movimiento(conc.get("nombre").toString(), Artj.get("descripcion").toString(), Integer.parseInt(Artj.get("importe").toString()));
                        movimientos.add(a);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(Fm_movimientos.this.getActivity(), "Tu nombre de usuario o la contraseña son incorrectos",
                        Toast.LENGTH_LONG).show();


            }

            ListView  lv = (ListView) getView().findViewById(R.id.movimientos);

            AdapterMovimientos adapter = new AdapterMovimientos(getActivity(), movimientos);

            lv.setAdapter(adapter);

        }

    }
}
