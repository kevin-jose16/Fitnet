package com.example.olave.laboratorioandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * {@link Fm_agregarActividad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fm_agregarActividad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fm_agregarActividad extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText Nombre;
    EditText Precio;
    EditText Dias;
    EditText Periodo;
    Button agregar;
    private OnFragmentInteractionListener mListener;

    public Fm_agregarActividad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fm_agregarActividad.
     */
    // TODO: Rename and change types and number of parameters
    public static Fm_agregarActividad newInstance(String param1, String param2) {
        Fm_agregarActividad fragment = new Fm_agregarActividad();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fm_agregar_actividad,container,false);
        Nombre= (EditText) v.findViewById(R.id.Nombre_agr_act);
        Precio=(EditText) v.findViewById(R.id.Precio_agr_act);
        Dias= (EditText) v.findViewById(R.id.Dias_agr_act);
        Periodo= (EditText) v.findViewById(R.id.Periodo_agr_act);
        agregar= (Button) v.findViewById(R.id.Agregar_act);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
new Clase1().execute(Nombre.getText().toString(),String.valueOf(Precio.getText().toString()),String.valueOf(Dias.getText().toString()),String.valueOf(Periodo.getText().toString()));
            }
        });
        return v;
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
                articulo.put("dias",params[2]);
                articulo.put("periodo",params[3]);
                art.put("actividad",articulo);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                URL url = new URL("http://fitnet.com.uy/api/actividades/crear");
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
                Toast.makeText(Fm_agregarActividad.this.getActivity(), "Agregado",
                        Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(Fm_agregarActividad.this.getActivity(), "Error",
                        Toast.LENGTH_LONG).show();
        }
    }

}
