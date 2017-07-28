package com.example.olave.laboratorioandroid;

import android.graphics.Bitmap;

/**
 * Created by prog on 05/07/2017.
 */

public class Cliente {

    private String imagen;
    private String nombre;
    private String cedula;
    private String celular;
    private String correo;
    private String mutual;
    private String sexo;
    private String fecha;
    private Bitmap img;
    public Cliente() {
    }

    public Cliente(String imagen, String nombre, String cedula, String correo, String mutual, String sexo, String fecha, String celular) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.mutual = mutual;
        this.sexo = sexo;
        this.fecha = fecha;
        this.celular = celular;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMutual() {
        return mutual;
    }

    public void setMutual(String mutual) {
        this.mutual = mutual;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
