package com.example.olave.laboratorioandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgregarUsuario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgregarUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarUsuario extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText Nombre;
    EditText Nick;
    EditText Pass;
    ImageView IMG;
    Button img;
    Button agregar;
    private OnFragmentInteractionListener mListener;

    public AgregarUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarUsuario newInstance(String param1, String param2) {
        AgregarUsuario fragment = new AgregarUsuario();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_agregar_usuario, container, false);
        agregar= (Button) v.findViewById(R.id.Agregar_user);
        IMG= (ImageView) v.findViewById(R.id.Img_User);
        Nombre= (EditText) v.findViewById(R.id.Nombre_agr_user);
        Nick= (EditText) v.findViewById(R.id.Nick_agr_usr);
        Pass= (EditText) v.findViewById(R.id.Pass_agr_usr);
        img= (Button) v.findViewById(R.id.add_img_user);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,1);

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           new Clase1().execute(Nick.getText().toString(),Pass.getText().toString(),Nombre.getText().toString());
                                       }
                                   }
        );

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selImage = BitmapFactory.decodeStream(is);

                        IMG.setImageBitmap(selImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
                articulo.put("username",params[0]);
                articulo.put("password",params[1]);
                articulo.put("nombre",params[2]);
                articulo.put("imagen","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAATklEQVR42u3OsQkAIAxE0UyT/dfJFo5gCgsRRFNoUvwHVx58EQAAgLLUZ74WnI1viainMdGoLzG3UV9jTlEpMbuo1Jg1qkTMHKUCAADq6Wq0NZ9p/1jtAAAAAElFTkSuQmCC");
                art.put("usuario",articulo);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                URL url = new URL("http://fitnet.com.uy/api/usuarios/crear");
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
                Toast.makeText(AgregarUsuario.this.getActivity(), "Agregado",
                        Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(AgregarUsuario.this.getActivity(), "Error",
                        Toast.LENGTH_LONG).show();
        }
    }

}
