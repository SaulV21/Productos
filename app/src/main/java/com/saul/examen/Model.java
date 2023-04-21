package com.saul.examen;

public class Model {
    private int id;
    private byte[] foto;
    private String nombre;
    private String precio;
    private String codigo;
    //Constructor

    public Model(int id, byte[] foto, String nombre, String precio, String codigo) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo = codigo;
    }
    //getter y setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
