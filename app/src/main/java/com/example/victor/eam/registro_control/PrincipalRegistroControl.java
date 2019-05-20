package com.example.victor.eam.registro_control;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.victor.eam.R;
import com.example.victor.eam.docente.ControlDeClase;
import com.example.victor.eam.entidades.AllFragments;
import com.example.victor.eam.entidades.PrincipalPantallas;

public class PrincipalRegistroControl extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AllFragments {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_registro_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Este es el fragmet que se carga de primero
        Fragment miFragment = new PrincipalPantallas();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            setTitle("Registro y control");
            Fragment miFragment = new PrincipalPantallas();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        } else if (id == R.id.nav_gallery) {
            Fragment miFragment = new RegistroEstudiantes();
            setTitle("Registro  de estudiantes");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        } else if (id == R.id.nav_slideshow) {
            Fragment miFragment = new CreacionMaterias();
            setTitle("Creacion de materias");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        } else if (id == R.id.nav_manage) {
            Fragment miFragment = new RegistroAulas();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        } else if (id == R.id.registroDocentes) {
            Fragment miFragment = new RegistroDocentes();
            setTitle("Registro de docente");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        }else if(id==R.id.crearCursos) {
            Fragment miFragment = new CrearCursos();
            setTitle("Crear cursos");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        }else if(id==R.id.matricularEstudiante) {
            Fragment miFragment = new MatriculaEstudiante();
            setTitle("Matricular estudiante");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        }else if(id==R.id.modRegMateriasEstudiantes) {
            Fragment miFragment = new ControlDeClase();
            setTitle("Modificar materias de estudiante");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        }else if(id==R.id.usuarios) {
            Fragment miFragment = new ModificarUsuarios();
            setTitle("Gestionar usuarios");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();

        }else if(id==R.id.contratoDocentes) {
            Fragment miFragment = new contratoDocentes();
            setTitle("Contrato docentes");
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRegistroControl, miFragment).commit();
        }


        else if (id == R.id.nav_share) {
            //Cerrar Sesion

        } else if (id == R.id.nav_send) {
            //Salir de la aplicacion

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
