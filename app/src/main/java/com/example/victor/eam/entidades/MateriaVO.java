package com.example.victor.eam.entidades;

public class MateriaVO {
    String nombre,codigo;
    int nota;
    int fallas;


    public MateriaVO() {
    }

    public MateriaVO(String nombre, String codigo, int nota, int fallas) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.nota = nota;
        this.fallas = fallas;
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getFallas() {
        return fallas;
    }

    public void setFallas(int fallas) {
        this.fallas = fallas;
    }
}
