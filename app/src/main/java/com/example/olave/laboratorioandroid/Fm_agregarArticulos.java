package com.example.olave.laboratorioandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fm_agregarArticulos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fm_agregarArticulos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fm_agregarArticulos extends android.app.Fragment {
    Button bt ;
    EditText nombre,precio;


    private OnFragmentInteractionListener mListener;

    public Fm_agregarArticulos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fm_agregarArticulos.
     */
    // TODO: Rename and change types and number of parameters
    public static Fm_agregarArticulos newInstance(String param1, String param2) {
        Fm_agregarArticulos fragment = new Fm_agregarArticulos();
        return fragment;
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_crear_articulo, container, false);
        bt= (Button) rootView.findViewById(R.id.Agregar_Art);
        nombre= (EditText) rootView.findViewById(R.id.nombre_nuevo_art);
        precio= (EditText) rootView.findViewById(R.id.precio_nuevo_art);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Clase1().execute(nombre.getText().toString(),precio.getText().toString());
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class Clase1 extends AsyncTask<String, Void, Integer> {
        int res;

        @Override
        protected Integer doInBackground(String... params) {
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "sesion", Context.MODE_PRIVATE);
            String user = sharedPref.getString("username", null);
            String password = sharedPref.getString("password", null);
            String token = KeyGenerator.getSecurePassword(user, password);

            JSONObject art = new JSONObject();
            JSONObject articulo =new JSONObject();
            try {
                art.put("key", token);
                articulo.put("nombre",params[0]);
                articulo.put("precio",params[1]);
                art.put("articulo",articulo);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                URL url = new URL("http://fitnet.com.uy/api/articulos/crear");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;");


                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(String.valueOf(art));
                out.close();
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                res = responseCode;

            } catch (IOException e) {
                e.printStackTrace();
            }


            return res;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 200) {
                getFragmentManager().popBackStack();
                Toast.makeText(Fm_agregarArticulos.this.getActivity(), "Agregado",
                        Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(Fm_agregarArticulos.this.getActivity(), "Error",
                        Toast.LENGTH_LONG).show();
        }
    }


}
