package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSocio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSocio#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentSocio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    ArrayList<Cliente> art = new ArrayList<Cliente>();


    public FragmentSocio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSocio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSocio newInstance(String param1, String param2) {
        FragmentSocio fragment = new FragmentSocio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

    FloatingActionButton agregarSocio;
    Fragment fragment=null;
    ImageButton search;
    ArrayList<Cliente> busqeda = new ArrayList<Cliente>();
    EditText texto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Clase1().execute();
        View rootView = inflater.inflate(R.layout.fragment_fragment_socio, container, false);

        agregarSocio = (FloatingActionButton) rootView.findViewById(R.id.agregarSocio);
        agregarSocio.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                fragment =new AgregarSocio();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).addToBackStack("lista").commit();
            }
        });

        search = (ImageButton) rootView.findViewById(R.id.btn_buscar_soc);
        texto = (EditText) rootView.findViewById(R.id.buscar_socio);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = texto.getText().toString();
                busqeda.removeAll(busqeda);
                for(int i=0;i<art.size();i++)
                {
                    String socio = art.get(i).getNombre();
                    if(socio.contains(nombre)){
                        busqeda.add(art.get(i));

                    }

                }
                ListView lv = (ListView) getView().findViewById(R.id.listaClientes);

                AdapterClientes adapter = new AdapterClientes(getActivity(),busqeda);

                lv.setAdapter(adapter);


                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Cliente itemSel = null;
                        if(busqeda.isEmpty()){
                            itemSel = art.get(i);
                        }
                        else{
                            itemSel = busqeda.get(i);
                        }
                        Fragment detalle = new DetalleCliente();
                        Bundle bun = new Bundle();
                        bun.putString("nombre",itemSel.getNombre());
                        bun.putString("cedula",itemSel.getCedula());
                        bun.putString("fecha",itemSel.getFecha());
                        bun.putString("correo",itemSel.getCorreo());

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        itemSel.getImg().compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        bun.putByteArray("imagen",byteArray);
                        bun.putString("mutual",itemSel.getMutual());
                        bun.putString("sexo",itemSel.getSexo());
                        bun.putString("celular",itemSel.getCelular());
                        detalle.setArguments(bun);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container,detalle).addToBackStack("lista").commit();
                    }
                });



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

    public class Clase1 extends AsyncTask<String, Void, List<Cliente>> {
        String res;

        @Override
        protected List<Cliente> doInBackground(String... params) {
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "sesion", Context.MODE_PRIVATE);
            String user = sharedPref.getString("username", null);
            String password = sharedPref.getString("password", null);
            String token = KeyGenerator.getSecurePassword(user, password);
            try {
                String KEY = token;
                URL url = new URL("http://fitnet.com.uy/api/clientes/listar/" + KEY);
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

            List<Cliente> clientes = new ArrayList<>();
            try {
                JSONArray json = new JSONArray(res);
                String imm;
                for (int i = 0; i < json.length(); i++) {
                    JSONObject cliJ = (JSONObject) json.get(i);
                        Cliente a = new Cliente(cliJ.get("imagen").toString(),cliJ.get("nombre").toString(),cliJ.get("cedula").toString(),cliJ.get("correo").toString(),cliJ.get("mutualista").toString(),cliJ.get("sexo").toString(),cliJ.get("fnac").toString(),cliJ.get("celular").toString());
                        clientes.add(a);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(Cliente item: clientes){
                Bitmap myBitmap=null;
                try {
                    URL url = new URL("http://fitnet.com.uy"+item.getImagen());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                    item.setImg(myBitmap);
                } catch (IOException e) {
                    //Excepcion al leer la imagen desde la url
                }
            }





            return clientes;
        }

        @Override
        protected void onPostExecute(List<Cliente> result) {
            art.removeAll(art);
            art.addAll(result);

            ListView lv = (ListView) getView().findViewById(R.id.listaClientes);

            AdapterClientes adapter = new AdapterClientes(getActivity(), art);

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Cliente itemSel = null;
                    if(busqeda.isEmpty()){
                        itemSel = art.get(i);
                    }
                    else{
                        itemSel = busqeda.get(i);
                    }
                    Fragment detalle = new DetalleCliente();
                    Bundle bun = new Bundle();
                    bun.putString("nombre",itemSel.getNombre());
                    bun.putString("cedula",itemSel.getCedula());
                    bun.putString("fecha",itemSel.getFecha());
                    bun.putString("correo",itemSel.getCorreo());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    itemSel.getImg().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    bun.putByteArray("imagen",byteArray);
                    bun.putString("mutual",itemSel.getMutual());
                    bun.putString("sexo",itemSel.getSexo());
                    bun.putString("celular",itemSel.getCelular());
                    detalle.setArguments(bun);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container,detalle).addToBackStack("lista").commit();
                }
            });



        }
    }




}
