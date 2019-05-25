package com.example.victor.eam.entidades;

public class MateriaVO {
    String nombreMateria,codigo,nombreDocente;
    int nota;
    int fallas;


    public MateriaVO() {
    }

    public MateriaVO(String nombreMateria, String codigo, String nombreDocente, int nota, int fallas) {
        this.nombreMateria = nombreMateria;
        this.codigo = codigo;
        this.nombreDocente = nombreDocente;
        this.nota = nota;
        this.fallas = fallas;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return nombreMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
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
