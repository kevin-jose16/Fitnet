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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fm_agregarMovimiento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fm_agregarMovimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fm_agregarMovimiento extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    Button bt ;
    EditText importe,desc;
    Spinner tipoconcepto, tipomov;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;
    protected List<Concepto> conceptos = new ArrayList<>();

    private OnFragmentInteractionListener mListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new Clase2().execute();
        View rootView = inflater.inflate(R.layout.fm_crear_movimiento, container, false);
        bt= (Button) rootView.findViewById(R.id.Agregar_Mov);
        importe= (EditText) rootView.findViewById(R.id.importe_mov);
        desc= (EditText) rootView.findViewById(R.id.desc_movimiento);
        tipomov = (Spinner) rootView.findViewById(R.id.spinnertipomov);
        tipoconcepto = (Spinner) rootView.findViewById(R.id.spinnerconcepto);
        final String[] tipomovi = new String[1];
        final String[] concept = new String[1];
        int num;
        tipomov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                tipomovi[0] = adapterView.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });
        tipoconcepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String completo = adapterView.getItemAtPosition(pos).toString();
                String[] parts = completo.split("-");
                concept[0] = parts[0];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Clase1().execute(concept[0],tipomovi[0], String.valueOf(desc.getText()),String.valueOf(importe.getText()));
            }
        });
        return rootView;
    }
    public Fm_agregarMovimiento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fm_agregarConcepto.
     */
    // TODO: Rename and change types and number of parameters
    public static Fm_agregarMovimiento newInstance(String param1, String param2,String param3, int param4) {
        Fm_agregarMovimiento fragment = new Fm_agregarMovimiento();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
        }
        /*Spinner spinner = (Spinner) findViewById(R.id.spinnerconcepto);

        spinner.setAdapter();*/
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

            JSONObject mov = new JSONObject();
            JSONObject movement =new JSONObject();
            JSONObject concepto =new JSONObject();
            try {
                mov.put("key", token);
                concepto.put("id",params[0]);
                movement.put("concepto",concepto);
                movement.put("tipo",params[1]);
                movement.put("descripcion",params[2]);
                movement.put("importe",params[3]);
                mov.put("movimiento",movement);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                URL url = new URL("http://fitnet.com.uy/api/movimientos/crear");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;");


                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(String.valueOf(mov));
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
                Toast.makeText(Fm_agregarMovimiento.this.getActivity(), "Agregado",
                        Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(Fm_agregarMovimiento.this.getActivity(), "Error",
                        Toast.LENGTH_LONG).show();
        }


    }
    public class Clase2 extends AsyncTask<String, Void, String> {
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
                URL url = new URL("http://fitnet.com.uy/api/conceptos/listar/" + KEY);
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
                    conceptos.removeAll(conceptos);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject Artj = (JSONObject) json.get(i);
                        Concepto a = new Concepto(Integer.parseInt(Artj.get("id").toString()),Artj.get("nombre").toString(), Artj.get("descripcion").toString());
                        conceptos.add(a);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(Fm_agregarMovimiento.this.getActivity(), "Tu nombre de usuario o la contraseña son incorrectos",
                        Toast.LENGTH_LONG).show();


            }
            Spinner spinner = (Spinner) getView().findViewById(R.id.spinnerconcepto);
            List<String> st = new ArrayList<String>();
            for(int i=0;i<conceptos.size();i++){
                String sp = conceptos.get(i).getId() + "-" + conceptos.get(i).getNombre();
                st.add(sp);
            }
            //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conceptos));
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, st); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
        }

    }
}