package com.example.olave.laboratorioandroid;

/**
 * Created by KevinQ on 2/7/2017.
 */

public class Articulo {
    private String Nombre;
    private String Precio;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public Articulo() {
    }

    public Articulo(String nombre, String precio) {
        Nombre = nombre;
        Precio = precio;
    }
}
