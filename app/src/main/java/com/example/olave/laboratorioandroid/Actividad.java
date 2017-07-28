package com.example.olave.laboratorioandroid;

/**
 * Created by KevinQ on 13/7/2017.
 */

public class Actividad {
    private String Nombre;
    private String Precio;
    private int Dias;
    private int Periodo;

    public Actividad(String nombre, String precio, int dias, int periodo) {
        Nombre = nombre;
        Precio = precio;
        Dias = dias;
        Periodo = periodo;
    }

    public Actividad() {
    }

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

    public int getDias() {
        return Dias;
    }

    public void setDias(int dias) {
        Dias = dias;
    }

    public int getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(int periodo) {
        Periodo = periodo;
    }
}
