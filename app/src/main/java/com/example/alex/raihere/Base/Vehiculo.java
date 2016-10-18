package com.example.alex.raihere.Base;

/**
 * Created by alejandro on 8/09/16.
 */
public class Vehiculo {
    private String id;
    private String marca;
    private String modelo;
    private String año;

    public Vehiculo(){

    }

    public Vehiculo(String id, String marca, String modelo, String año) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }
}
