package com.gmail.orlandroyd.ohcc.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.fragment.AccountEditFragment;
import com.gmail.orlandroyd.ohcc.fragment.AccountFragment;
import com.gmail.orlandroyd.ohcc.fragment.UserLoginFragment;
import com.gmail.orlandroyd.ohcc.fragment.UserRegistrationFragment;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by OrlanDroyd on 15/04/2019.
 */
public class AuthenticationActivity extends AppCompatActivity implements UserLoginFragment.OnLoginFormActivityListener, AccountFragment.OnLogoutListener, UserRegistrationFragment.OnRegistrationListener, AccountEditFragment.OnEditFormActivityListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        Toolbar toolbar = findViewById(R.id.toolbar_authentication);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (AppPreferences.getLoginStatus(getApplicationContext())) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AccountFragment()).commit();
                getSupportActionBar().setTitle(getString(R.string.my_account));
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new UserLoginFragment()).commit();
                getSupportActionBar().setTitle(getString(R.string.login_login));
            }
        }
    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserRegistrationFragment()).commit();
        getSupportActionBar().setTitle(getString(R.string.login_activity));
    }

    @Override
    public void performLogin(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol) {
        saveDataLogin(idUsuario, nombre, apellido1, apellido2, nombreUsuario, idRol);
    }


    @Override
    public void logoutPerformed() {
        final String def = "";
        final String nombre = "Registrarse...";
        final String nombreUsuario = "Click aquí";
        AppPreferences.saveLoginStatus(false, getApplicationContext());
        AppPreferences.saveIdUsuario(-1, getApplicationContext());
        AppPreferences.saveNombre(nombre, getApplicationContext());
        AppPreferences.saveApellido1(def, getApplicationContext());
        AppPreferences.saveApellido2(def, getApplicationContext());
        AppPreferences.saveNombreUsuario(nombreUsuario, getApplicationContext());

        AppPreferences.saveRol(-1, getApplicationContext());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserLoginFragment()).commit();
        getSupportActionBar().setTitle(getString(R.string.login_login));
    }


    private void saveDataLogin(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol) {
        // Usuario
        AppPreferences.saveIdUsuario(idUsuario, getApplicationContext());
        AppPreferences.saveNombre(nombre, getApplicationContext());
        AppPreferences.saveApellido1(apellido1, getApplicationContext());
        AppPreferences.saveApellido2(apellido2, getApplicationContext());
        AppPreferences.saveNombreUsuario(nombreUsuario, getApplicationContext());
        AppPreferences.saveRol(idRol, getApplicationContext());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
        getSupportActionBar().setTitle(getString(R.string.my_account));
    }

    private void saveDataRegisterCiudadano(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol, String ci, String dirPart, String telef, String email, int tipo) {
        // Usuario
        AppPreferences.saveIdUsuario(idUsuario, getApplicationContext());
        AppPreferences.saveNombre(nombre, getApplicationContext());
        AppPreferences.saveApellido1(apellido1, getApplicationContext());
        AppPreferences.saveApellido2(apellido2, getApplicationContext());
        AppPreferences.saveNombreUsuario(nombreUsuario, getApplicationContext());
        AppPreferences.saveRol(idRol, getApplicationContext());
        // Ciudadano
        AppPreferences.saveCi(ci, getApplicationContext());
        AppPreferences.saveDirPart(dirPart, getApplicationContext());
        AppPreferences.saveTelef(telef, getApplicationContext());
        AppPreferences.saveEmail(email, getApplicationContext());
        AppPreferences.saveTipoCid(tipo, getApplicationContext());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
        getSupportActionBar().setTitle(getString(R.string.my_account));
    }

    @Override
    public void registrationPerformed(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol, String ci, String dir_part, String telef, String email, int tipo_ciudadano) {
        saveDataRegisterCiudadano(idUsuario, nombre, apellido1, apellido2, nombreUsuario, idRol, ci, dir_part, telef, email, tipo_ciudadano);
    }

    @Override
    public void editPerformed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountEditFragment()).commit();
        getSupportActionBar().setTitle(getString(R.string.editar));
    }

    @Override
    public void performLoginEdit(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol) {
        saveDataLogin(idUsuario, nombre, apellido1, apellido2, nombreUsuario, idRol);
    }
}
