package com.example.victor.eam.entidades;

public class ProgramaVo {
    String nombre,id;

    public ProgramaVo() {
    }

    public ProgramaVo(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
