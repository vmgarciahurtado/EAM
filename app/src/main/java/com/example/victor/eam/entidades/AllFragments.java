package com.example.victor.eam.entidades;

import com.example.victor.eam.director_programa.AsignarMaterias;
import com.example.victor.eam.director_programa.CreacionCursos;
import com.example.victor.eam.docente.ConsultarHorario;
import com.example.victor.eam.docente.CrearAgenda;
import com.example.victor.eam.docente.RegistroNotas;
import com.example.victor.eam.docente.TomasLista;
import com.example.victor.eam.estudiante.ConsultarSeguimiento;
import com.example.victor.eam.estudiante.RegistroMaterias;
import com.example.victor.eam.registro_control.CreacionMaterias;
import com.example.victor.eam.registro_control.RegistroAulas;
import com.example.victor.eam.registro_control.RegistroDocentes;
import com.example.victor.eam.registro_control.RegistroEstudiantes;

public interface AllFragments extends
        AsignarMaterias.OnFragmentInteractionListener,
        CreacionCursos.OnFragmentInteractionListener,
        ConsultarHorario.OnFragmentInteractionListener,
        RegistroNotas.OnFragmentInteractionListener,
        TomasLista.OnFragmentInteractionListener,
        com.example.victor.eam.estudiante.ConsultarHorario.OnFragmentInteractionListener,
        ConsultarSeguimiento.OnFragmentInteractionListener,
        RegistroMaterias.OnFragmentInteractionListener,
        CreacionMaterias.OnFragmentInteractionListener,
        RegistroAulas.OnFragmentInteractionListener,
        RegistroDocentes.OnFragmentInteractionListener,
        RegistroEstudiantes.OnFragmentInteractionListener,
        PrincipalPantallas.OnFragmentInteractionListener,
        CrearAgenda.OnFragmentInteractionListener
{
}
