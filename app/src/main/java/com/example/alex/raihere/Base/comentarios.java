package com.example.alex.raihere.Base;

/**
 * Created by alejandro on 22/09/16.
 */

public class comentarios {

    private String comentario;
    private String nombre;
    private String imagen;
    private String fecha;


    public comentarios(){

}


    public comentarios(String comentario, String nombre, String imagen, String fecha) {
        this.comentario = comentario;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
