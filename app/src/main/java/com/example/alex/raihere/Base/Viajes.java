package com.example.alex.raihere.Base;

/**
 * Created by alejandro on 8/09/16.
 */
public class Viajes {

    private String id;
    private String idusuario;
    private String nombre;
    private String telefono;
    private String fecha;
    private String inicio;
    private String destino;
    private String hora;
    private String costo;
    private Integer cantidad;
    private String escalas;
    private String lista;
    private String descripcion;
    private String cigarro;
    private String mascota;
    private String alcohol;
    private String maleta;
    private String marca;
    private String modelo;
    private String anio;
    private String foto;
    private String imagen;
    private String registro;


    public Viajes(){

    }

    public Viajes(String id, String idusuario, String nombre, String telefono,String fecha, String inicio, String destino, String hora, String costo, Integer cantidad, String escalas, String lista, String descripcion, String cigarro, String mascota, String alcohol, String maleta, String marca, String modelo, String anio, String foto,String imagen, String registro) {
        this.id = id;
        this.idusuario=idusuario;
        this.nombre = nombre;
        this.telefono=telefono;
        this.imagen=imagen;
        this.fecha = fecha;
        this.inicio = inicio;
        this.destino = destino;
        this.hora = hora;
        this.costo = costo;
        this.cantidad = cantidad;
        this.escalas = escalas;
        this.lista = lista;
        this.descripcion = descripcion;
        this.cigarro = cigarro;
        this.mascota = mascota;
        this.alcohol = alcohol;
        this.maleta = maleta;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.foto=foto;
        this.registro=registro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEscalas() {
        return escalas;
    }

    public void setEscalas(String escalas) {
        this.escalas = escalas;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCigarro() {
        return cigarro;
    }

    public void setCigarro(String cigarro) {
        this.cigarro = cigarro;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getMaleta() {
        return maleta;
    }

    public void setMaleta(String maleta) {
        this.maleta = maleta;
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

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getFoto(){
        return foto;
    }

    public void setFoto(String foto){
        this.foto=foto;
    }

    public String getRegistro(){
        return registro;
    }

    public void setRegistro(String registro){
        this.registro=registro;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
