package com.example.victor.eam.estudiante;

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
import com.example.victor.eam.docente.ConsultarHorario;
import com.example.victor.eam.entidades.AllFragments;
import com.example.victor.eam.entidades.PrincipalPantallas;

public class PrincipalEstudiante extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AllFragments {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_estudiante);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.containerEstudiante, miFragment).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.containerEstudiante, miFragment).commit();

        } else if (id == R.id.nav_gallery) {
            Fragment miFragment = new RegistroMaterias();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerEstudiante, miFragment).commit();

  /*      } else if (id == R.id.nav_slideshow) {
            Fragment miFragment = new ConsultarSeguimiento();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerEstudiante, miFragment).commit();

        }*/
        }else if (id == R.id.nav_manage) {
            Fragment miFragment = new com.example.victor.eam.estudiante.GestionMaterias();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerEstudiante, miFragment).commit();

        } else if (id == R.id.nav_share) {
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
