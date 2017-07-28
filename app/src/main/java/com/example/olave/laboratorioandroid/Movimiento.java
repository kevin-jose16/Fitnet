package com.example.olave.laboratorioandroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olave on 9/7/2017.
 */

public class Movimiento {

    private String concepto;
    private String descripcion;
    private int importe;
    private String tipo;

    //private List<String> conceptos;

    public Movimiento(String concepto, String descripcion, int importe){
        this.concepto = concepto;
        this.descripcion = descripcion;
        this.importe = importe;
        //conceptos = new ArrayList<String>();
    }
    public Movimiento(){
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /*public List<String> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<String> conceptos) {
        this.conceptos = conceptos;
    }

    public void addconcept(String concepto){
        this.conceptos.add(concepto);
    }*/
}
