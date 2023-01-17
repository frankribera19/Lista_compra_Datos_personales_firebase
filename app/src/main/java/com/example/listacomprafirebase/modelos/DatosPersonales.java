package com.example.listacomprafirebase.modelos;

public class DatosPersonales {
    private String nombre;
    private String spellidos;
    private int num_telefono;

    public DatosPersonales() {
    }

    public DatosPersonales(String nombre, String spellidos, int num_telefono) {
        this.nombre = nombre;
        this.spellidos = spellidos;
        this.num_telefono = num_telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSpellidos() {
        return spellidos;
    }

    public void setSpellidos(String spellidos) {
        this.spellidos = spellidos;
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
                ", spellidos='" + spellidos + '\'' +
                ", num_telefono=" + num_telefono +
                '}';
    }
}
