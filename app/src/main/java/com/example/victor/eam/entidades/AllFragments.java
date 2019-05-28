package com.example.victor.eam.entidades;

import com.example.victor.eam.director_programa.AsignarMaterias;
import com.example.victor.eam.docente.ConsultarHorario;
import com.example.victor.eam.docente.ControlDeClase;
import com.example.victor.eam.docente.CrearAgenda;
import com.example.victor.eam.docente.FragmentReporteCursos;
import com.example.victor.eam.docente.RegistrarNotasFallas;
import com.example.victor.eam.docente.ReporteGanadores;
import com.example.victor.eam.docente.ReporteNotasCurso;
import com.example.victor.eam.docente.VerCursos;
import com.example.victor.eam.docente.ReportesDocentes;
import com.example.victor.eam.docente.TomasLista;
import com.example.victor.eam.estudiante.ConsultarSeguimiento;
import com.example.victor.eam.estudiante.GestionMaterias;
import com.example.victor.eam.estudiante.RegistroMaterias;
import com.example.victor.eam.registro_control.CreacionMaterias;
import com.example.victor.eam.registro_control.CrearCursos;
import com.example.victor.eam.registro_control.MatriculaEstudiante;
import com.example.victor.eam.registro_control.ModificarDocente;
import com.example.victor.eam.registro_control.ModificarEstudiante;
import com.example.victor.eam.registro_control.ModificarUsuarios;
import com.example.victor.eam.registro_control.RegistroAulas;
import com.example.victor.eam.registro_control.RegistroDocentes;
import com.example.victor.eam.registro_control.RegistroEstudiantes;
import com.example.victor.eam.registro_control.RegistroMateriasEst;
import com.example.victor.eam.registro_control.contratoDocentes;

public interface AllFragments extends
        AsignarMaterias.OnFragmentInteractionListener,
        ConsultarHorario.OnFragmentInteractionListener,
        VerCursos.OnFragmentInteractionListener,
        TomasLista.OnFragmentInteractionListener,
        com.example.victor.eam.estudiante.ConsultarHorario.OnFragmentInteractionListener,
        ConsultarSeguimiento.OnFragmentInteractionListener,
        RegistroMaterias.OnFragmentInteractionListener,
        CreacionMaterias.OnFragmentInteractionListener,
        RegistroAulas.OnFragmentInteractionListener,
        RegistroDocentes.OnFragmentInteractionListener,
        RegistroEstudiantes.OnFragmentInteractionListener,
        PrincipalPantallas.OnFragmentInteractionListener,
        CrearAgenda.OnFragmentInteractionListener,
        ReportesDocentes.OnFragmentInteractionListener,
        ControlDeClase.OnFragmentInteractionListener,
        CrearCursos.OnFragmentInteractionListener,
        MatriculaEstudiante.OnFragmentInteractionListener,
        ModificarUsuarios.OnFragmentInteractionListener,
        ModificarDocente.OnFragmentInteractionListener,
        ModificarEstudiante.OnFragmentInteractionListener,
        contratoDocentes.OnFragmentInteractionListener,
        GestionMaterias.OnFragmentInteractionListener,
        RegistroMateriasEst.OnFragmentInteractionListener,
        RegistrarNotasFallas.OnFragmentInteractionListener,
        ReporteNotasCurso.OnFragmentInteractionListener,
        ReporteGanadores.OnFragmentInteractionListener,
        FragmentReporteCursos.OnFragmentInteractionListener
{
}
