package com.example.olave.laboratorioandroid;

/**
 * Created by KevinQ on 5/7/2017.
 */

public class Concepto {
    private String Nombre;
    private String Descripcion;
    private int Id;
    public Concepto(String nombre, String descripcion) {
        Nombre = nombre;
        Descripcion = descripcion;
    }

    public Concepto(int id, String nombre, String descripcion) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;

    }

    public Concepto() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }
}
