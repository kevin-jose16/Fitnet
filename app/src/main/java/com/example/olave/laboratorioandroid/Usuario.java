package com.example.olave.laboratorioandroid;

import android.graphics.Bitmap;

/**
 * Created by KevinQ on 13/7/2017.
 */

public class Usuario {
    private String Nombre;
    private String Username;
    private String Password;
    private String Imagen;
    private Bitmap Img;

    public Usuario(String nombre, String username, String password, String imagen) {
        Nombre = nombre;
        Username = username;
        Password = password;
        Imagen = imagen;
    }

    public Bitmap getImg() {
        return Img;
    }

    public void setImg(Bitmap img) {
        Img = img;
    }

    public Usuario() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
