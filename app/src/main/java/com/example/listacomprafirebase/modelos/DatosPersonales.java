package com.example.listacomprafirebase.modelos;

public class DatosPersonales {
    private String nombre;
    private String apellidos;
    private int num_telefono;

    public DatosPersonales() {
    }

    public DatosPersonales(String nombre, String apellidos, int num_telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono = num_telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getapellidos() {
        return apellidos;
    }

    public void setapellidos(String spellidos) {
        this.apellidos = spellidos;
    }

    public int getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(int num_telefono) {
        this.num_telefono = num_telefono;
    }

    @Override
    public String toString() {
        return "DatosPersonales{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", num_telefono=" + num_telefono +
                '}';
    }
}
