package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import static android.app.Activity.RESULT_OK;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AgregarSocio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private final int SELECT_PHOTO = 1;
    ImageView imageView;
    Button botonImg;
    EditText nombre;
    EditText cedula;
    EditText fechaNac;
    EditText correo;
    EditText celular;
    EditText mutualista;
    Spinner sexo;
    Button agregar;

    Bitmap imgBitmap;
    Bitmap imagenSel;

    public AgregarSocio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarSocio.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarSocio newInstance(String param1, String param2) {
        AgregarSocio fragment = new AgregarSocio();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_agregar_socio, container, false);
        nombre = (EditText) rootView.findViewById(R.id.nombre);
        cedula = (EditText) rootView.findViewById(R.id.cedula);
        fechaNac = (EditText) rootView.findViewById(R.id.fecha);
        correo = (EditText) rootView.findViewById(R.id.correo);
        celular = (EditText) rootView.findViewById(R.id.celular);
        mutualista = (EditText) rootView.findViewById(R.id.mutualista);
        sexo = (Spinner) rootView.findViewById(R.id.sexo);
        agregar = (Button) rootView.findViewById(R.id.agregar);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        botonImg = (Button) rootView.findViewById(R.id.btn_pick);
        botonImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Clase1().execute(nombre.getText().toString(),cedula.getText().toString(),fechaNac.getText().toString(),correo.getText().toString(),celular.getText().toString(),mutualista.getText().toString(),sexo.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        imgBitmap = selectedImage;
                        imageView.setImageBitmap(imgBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
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

            //paso imagen a string base 64

            Bitmap immagex = imgBitmap;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


            JSONObject soc = new JSONObject();
            JSONObject socio =new JSONObject();
            try {
                soc.put("key", token);
                socio.put("nombre",params[0]);
                socio.put("cedula",params[1]);
                socio.put("fnac",params[2]);
                socio.put("correo",params[3]);
                socio.put("celular",params[4]);
                socio.put("mutualista",params[5]);
                socio.put("sexo",params[6]);
                socio.put("imagen","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAATklEQVR42u3OsQkAIAxE0UyT/dfJFo5gCgsRRFNoUvwHVx58EQAAgLLUZ74WnI1viainMdGoLzG3UV9jTlEpMbuo1Jg1qkTMHKUCAADq6Wq0NZ9p/1jtAAAAAElFTkSuQmCC");
                soc.put("cliente",socio);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                URL url = new URL("http://fitnet.com.uy/api/clientes/crear");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;");


                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(String.valueOf(soc));
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
                Toast.makeText(AgregarSocio.this.getActivity(), "Agregado",
                        Toast.LENGTH_LONG).show();


            } else
                Toast.makeText(AgregarSocio.this.getActivity(), "Error",
                        Toast.LENGTH_LONG).show();
        }



    }

}
