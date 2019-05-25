package com.example.victor.eam.entidades;

public class DetalleMateriaVo {
    String nota,nombre,horario,fallas;

    public DetalleMateriaVo() {
    }

    public DetalleMateriaVo(String nota, String nombre, String horario, String fallas) {
        this.nota = nota;
        this.nombre = nombre;
        this.horario = horario;
        this.fallas = fallas;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFallas() {
        return fallas;
    }

    public void setFallas(String fallas) {
        this.fallas = fallas;
    }
}
