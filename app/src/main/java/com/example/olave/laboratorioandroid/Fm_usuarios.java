package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fm_usuarios extends android.app.Fragment {
    ImageButton agregar, search;
    ArrayList<Usuario> art = new ArrayList<>();
    ArrayList<Usuario> busqeda = new ArrayList<>();
    EditText texto;
    Fragment fragment=null;
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
new Clase1().execute();
        View rootView = inflater.inflate(R.layout.fm_usuarios, container, false);
agregar= (ImageButton) rootView.findViewById(R.id.ADD_USER);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment =new AgregarUsuario();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,fragment).addToBackStack("lista2").commit();
            }
        });
      lv = (ListView) rootView.findViewById(R.id.Usuarios);
   search= (ImageButton) rootView.findViewById(R.id.Search_user);
        texto= (EditText) rootView.findViewById(R.id.Busqueda_user);
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
                ListView lv = (ListView) getView().findViewById(R.id.Usuarios);

                AdapterUsuario adapter = new AdapterUsuario(busqeda,getActivity());

                lv.setAdapter(adapter);

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
                URL url = new URL("http://fitnet.com.uy/api/usuarios/listar/" + KEY);
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

            try {
                JSONArray json = new JSONArray(res);
                art.removeAll(art);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject Artj = (JSONObject) json.get(i);
                    Usuario a = new Usuario(Artj.get("nombre").toString(), Artj.get("username").toString(),Artj.get("password").toString(), Artj.get("imagen").toString());

                    art.add(a);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (Usuario item: art){
                Bitmap bit=null;
                try {
                    URL url=new URL("http://fitnet.com.uy"+item.getImagen());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.connect();
                    InputStream in= con.getInputStream();
                    bit= BitmapFactory.decodeStream(in);
                    item.setImg(bit);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {


            AdapterUsuario adapter = new AdapterUsuario(art,getActivity());

            lv.setAdapter(adapter);

        }

    }
}