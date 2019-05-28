package com.example.victor.eam.entidades;

public class CursoVO {
    String nombre, codigo;

    public CursoVO(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public CursoVO() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
}
