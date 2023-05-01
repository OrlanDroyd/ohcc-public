package com.gmail.orlandroyd.ohcc.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.fragment.AboutFragment;
import com.gmail.orlandroyd.ohcc.fragment.BookTabFragment;
import com.gmail.orlandroyd.ohcc.fragment.ContactFragment;
import com.gmail.orlandroyd.ohcc.fragment.GalleryFragment;
import com.gmail.orlandroyd.ohcc.fragment.GlossaryFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListDirectivoFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListEspecialistaFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListSolicitudByCiudadanoFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListSolicitudByEspecialistaFragment;
import com.gmail.orlandroyd.ohcc.fragment.MainFragment;
import com.gmail.orlandroyd.ohcc.fragment.MapFragment;
import com.gmail.orlandroyd.ohcc.fragment.ReportFragment;
import com.gmail.orlandroyd.ohcc.fragment.HelpFragment;
import com.gmail.orlandroyd.ohcc.fragment.interfaces.IFragments;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

/**
 * Created by OrlanDroyd on 10/2/2019.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragments {

    private LinearLayout loginNavHeader;
    private AppCompatTextView tvNombreNavH, tvRolNavH;
    private NavigationView navigationView;
    private Toast toastExit;
    private long timeExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment miFragment = new MainFragment();
        getSupportActionBar().setTitle(getString(R.string.home));
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        loginNavHeader = headerView.findViewById(R.id.loginNavHeader);
        tvNombreNavH = headerView.findViewById(R.id.tvNombreLoginNavHeader);
        tvRolNavH = headerView.findViewById(R.id.tvRolLoginNavHeader);

        getNavMenu();

        loginNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void getNavMenu() {
        String nombre = AppPreferences.getNombre(getApplicationContext());
        String nombreUsuario = AppPreferences.getNombreUsuario(getApplicationContext());

        tvNombreNavH.setText(nombre);
        tvRolNavH.setText(nombreUsuario);

        int rol = AppPreferences.getRol(this);
        navigationView.getMenu().clear();

        switch (rol) {
            case 1:
                // Directivo
                navigationView.inflateMenu(R.menu.drawer_directivo_logged);
                break;
            case 2:
                // Especialista
                navigationView.inflateMenu(R.menu.drawer_especialista_logged);
                break;
            case 3:
                // Ciudadano
                navigationView.inflateMenu(R.menu.drawer_usuario_logged);
                break;
            case 4:
                // Administrador
                navigationView.inflateMenu(R.menu.drawer_directivo_logged);
                break;
            default:
                // Visitante
                navigationView.inflateMenu(R.menu.drawer_default);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNavMenu();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getNavMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (timeExit + 2000 > System.currentTimeMillis()) {
                toastExit.cancel();
                super.onBackPressed();
                return;
            } else {
                toastExit = Toast.makeText(getApplicationContext(), "Presiona otra vez para salir", Toast.LENGTH_SHORT);
                toastExit.show();
            }
            timeExit = System.currentTimeMillis();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment myFragment = null;
        boolean fragmentSelected = false;
        String title = "";

        if (id == R.id.nav_home) {
            myFragment = new MainFragment();
            fragmentSelected = true;
            title = getString(R.string.home);
        } else if (id == R.id.nav_books) {
            myFragment = new BookTabFragment();
            fragmentSelected = true;
            title = getString(R.string.books);
        } else if (id == R.id.nav_glossary) {
            myFragment = new GlossaryFragment();
            fragmentSelected = true;
            title = getString(R.string.glossary);
        } else if (id == R.id.nav_map) {
            myFragment = new MapFragment();
            fragmentSelected = true;
            title = getString(R.string.map);
        } else if (id == R.id.nav_contact) {
            myFragment = new ContactFragment();
            fragmentSelected = true;
            title = getString(R.string.contact);
        } else if (id == R.id.nav_gallery) {
            myFragment = new GalleryFragment();
            fragmentSelected = true;
            title = getString(R.string.gallery);
        } else if (id == R.id.nav_setting) {
            myFragment = new HelpFragment();
            fragmentSelected = true;
            title = getString(R.string.help);
        } else if (id == R.id.nav_about) {
            myFragment = new AboutFragment();
            fragmentSelected = true;
            title = getString(R.string.about);
        } else if (id == R.id.nav_exit) {
            finish();
        }

        // Ciudadano
        else if (id == R.id.nav_step1) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_1);
            AppPreferences.saveFragmetPos(1, getApplicationContext());
        } else if (id == R.id.nav_step2) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_2);
            AppPreferences.saveFragmetPos(2, getApplicationContext());
        } else if (id == R.id.nav_step3) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_3);
            AppPreferences.saveFragmetPos(3, getApplicationContext());
        } else if (id == R.id.nav_step4) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_4);
            AppPreferences.saveFragmetPos(4, getApplicationContext());
        } else if (id == R.id.nav_step5) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_5);
            AppPreferences.saveFragmetPos(5, getApplicationContext());
        } else if (id == R.id.nav_step6) {
            myFragment = new ListSolicitudByCiudadanoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_6);
            AppPreferences.saveFragmetPos(6, getApplicationContext());
        }

        // Especialista
        else if (id == R.id.nav_step1_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_1_especialista);
            AppPreferences.saveFragmetPos(1, getApplicationContext());
        } else if (id == R.id.nav_step2_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_2_especialista);
            AppPreferences.saveFragmetPos(2, getApplicationContext());
        } else if (id == R.id.nav_step3_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_3_especialista);
            AppPreferences.saveFragmetPos(3, getApplicationContext());
        } else if (id == R.id.nav_step4_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_4_especialista);
            AppPreferences.saveFragmetPos(4, getApplicationContext());
        } else if (id == R.id.nav_step5_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_5_especialista);
            AppPreferences.saveFragmetPos(5, getApplicationContext());
        } else if (id == R.id.nav_step6_especialista) {
            myFragment = new ListSolicitudByEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_6_especialista);
            AppPreferences.saveFragmetPos(6, getApplicationContext());
        }

        // Directivo
        else if (id == R.id.nav_step1_directivo) {
            myFragment = new ReportFragment();
            fragmentSelected = true;
            title = getString(R.string.step_1_directivo);
        } else if (id == R.id.nav_step2_directivo) {
            myFragment = new ListEspecialistaFragment();
            fragmentSelected = true;
            title = getString(R.string.step_2_directivo);
        } else if (id == R.id.nav_step3_directivo) {
            myFragment = new ListDirectivoFragment();
            fragmentSelected = true;
            title = getString(R.string.step_3_directivo);
        }


        if (fragmentSelected == true) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
            getSupportActionBar().setTitle(title);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Required method onFragmentInteraction
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }


}
