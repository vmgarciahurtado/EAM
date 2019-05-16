package com.example.victor.eam.docente;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.victor.eam.R;
import com.example.victor.eam.entidades.AllFragments;
import com.example.victor.eam.entidades.PrincipalPantallas;
import com.example.victor.eam.registro_control.MatriculaEstudiante;
import com.example.victor.eam.registro_control.ModificarUsuarios;

public class PrincipalDocente extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AllFragments {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_docente);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();
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
            Fragment miFragment = new PrincipalPantallas();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        } else if (id == R.id.nav_gallery) {
            Fragment miFragment = new RegistroNotas();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        } else if (id == R.id.nav_slideshow) {
            Fragment miFragment = new TomasLista();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        } else if (id == R.id.nav_manage) {
            Fragment miFragment = new ConsultarHorario();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        } else if(id==R.id.agregarAgenda){
            Fragment miFragment = new CrearAgenda();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.reportes) {
            Fragment miFragment = new ReportesDocentes();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.registrarInasistencia) {
            Fragment miFragment = new RegistrarInasistencias();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.controlClase) {
            Fragment miFragment = new ControlDeClase();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.crearCursos) {
            Fragment miFragment = new ControlDeClase();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.matricularEstudiante) {
            Fragment miFragment = new MatriculaEstudiante();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.modRegMateriasEstudiantes) {
            Fragment miFragment = new ControlDeClase();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();

        }else if(id==R.id.usuarios) {
            Fragment miFragment = new ModificarUsuarios();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerDocente, miFragment).commit();
        }

        else if (id == R.id.nav_share) {
            //Cerrar sesion
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
