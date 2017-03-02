package com.oswaldogh89.jobs.Home.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.oswaldogh89.jobs.R;
import com.oswaldogh89.jobs.Tutorial.Views.TutorialActivity;
import com.oswaldogh89.jobs.Utils.Constant;
import com.oswaldogh89.jobs.MisGastos.Views.MisGastosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        LaunchTutoIfFirstTime();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Pone el primer ITEM del navigationDrawer
        navigationView.getMenu().getItem(0).setChecked(true);
        setFragment(0);
    }

    @OnClick(R.id.fab)
    public void AgregarNuevoEmpleo(View view) {
        Snackbar.make(view, "Agregar Nuevo empleo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void LaunchTutoIfFirstTime() {
        Intent i = new Intent(this, TutorialActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_map:
                setFragment(Constant.VIEW_MAP);
                break;
            case R.id.nav_mis_publicaciones:
                setFragment(Constant.VIEW_MISPUBLICACIONES);
                break;
            case R.id.nav_mensajes:
                setFragment(Constant.VIEW_MISMENSAJES);
                break;
            case R.id.nav_mi_perfil:
                setFragment(Constant.VIEW_MIPERFIL);
                break;
            case R.id.nav_mis_herramientas:
                setFragment(Constant.VIEW_MISHERRAMIENTAS);
                break;
            case R.id.nav_acerca_de_mi:
                setFragment(Constant.VIEW_ACERCADEMI);
                break;
            case R.id.nav_salir:
                setFragment(Constant.VIEW_SALIR);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case Constant.VIEW_MAP:
                setTitle("Mis gastos");
                MisGastosFragment frag_offers = new MisGastosFragment();
                fragmentTransaction.replace(R.id.fragment, frag_offers);
                break;
            case Constant.VIEW_MISPUBLICACIONES:
                setTitle("Mis publicaciones");
                break;
        }
        fragmentTransaction.commit();
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
}
