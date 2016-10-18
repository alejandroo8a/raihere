package com.example.alex.raihere.Base;

/**
 * Created by alejandro on 8/09/16.
 */
public class Usuario {



    private String id;
    private String email;
    private String nombre;
    private String vehiculo;
    private String telefono;
    private String imagen;

    public Usuario(){

    }

    public Usuario(String id, String email, String nombre, String vehiculo, String telefono, String imagen) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
