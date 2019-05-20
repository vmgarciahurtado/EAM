package com.example.victor.eam.entidades;

public class EstudianteVO {
    private int codigoEstudiante, cedulaEstudiante, programa, semestre;
    private String nombreEstudiante, fechaNacimiento, estadoEstado, direccion, telefono, correo;

    public EstudianteVO(int codigoEstudiante, int cedulaEstudiante, int programa, int semestre, String nombreEstudiante, String fechaNacimiento, String estadoEstado, String direccion, String telefono, String correo) {
        this.codigoEstudiante = codigoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.programa = programa;
        this.semestre = semestre;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoEstado = estadoEstado;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public EstudianteVO() {
    }

    public int getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(int codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public int getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(int cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
        this.programa = programa;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoEstado() {
        return estadoEstado;
    }

    public void setEstadoEstado(String estadoEstado) {
        this.estadoEstado = estadoEstado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
