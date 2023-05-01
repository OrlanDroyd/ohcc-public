package com.gmail.orlandroyd.ohcc.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.helper.InputValidations;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.DirectivoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioDirectivoCrearResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.gmail.orlandroyd.ohcc.util.AESUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OrlanDroyd on 20/4/2019.
 */
public class GestionarDirectivoActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog pDialog;

    private TextInputEditText etName, etLastName1, etLastName2, etUserName, etPassword1, etPassword2, etCargo;
    private TextInputLayout input_name, input_lastName1, input_lastName2, input_userName, input_password, input_password2, input_Cargo;
    private CardView btnSend;
    private LinearLayout pass1, pass2;

    private int ID = -1;

    private InputValidations inputValidations;

    public static final String EXTRA_ID = "ID_USUARIO";
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_APELLIDO1 = "APELLIDO1";
    public static final String EXTRA_APELLIDO2 = "APELLIDO2";
    public static final String EXTRA_NOMBRE_USUARIO = "NOMBRE_USUARIO";
    public static final String EXTRA_PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directivo_gestionar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        initView();
        initDialog();

        ID = getIntent().getIntExtra(EXTRA_ID, -1);

        btnSend.setOnClickListener(this);

        if (getIntent().hasExtra(EXTRA_ID)) {
            updateView();
            pass1.setVisibility(View.GONE);
            pass2.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle(getString(R.string.nuevo));
        }
    }

    private void updateView() {
        showDialog(getString(R.string.loading));
        getSupportActionBar().setTitle(getString(R.string.editar));
        etName.setText(getIntent().getStringExtra(EXTRA_NOMBRE));
        etLastName1.setText(getIntent().getStringExtra(EXTRA_APELLIDO1));
        etLastName2.setText(getIntent().getStringExtra(EXTRA_APELLIDO2));
        etUserName.setText(getIntent().getStringExtra(EXTRA_NOMBRE_USUARIO));
        etPassword1.setText(getIntent().getStringExtra(EXTRA_PASSWORD));
        etPassword2.setText(getIntent().getStringExtra(EXTRA_PASSWORD));

        Call<DirectivoResponse> call = RetrofitClient.getInstance().getApi().getDirectivoByID(ID);
        call.enqueue(new Callback<DirectivoResponse>() {
            @Override
            public void onResponse(Call<DirectivoResponse> call, Response<DirectivoResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        String cargo = response.body().getData().getCargo();

                        etCargo.setText(cargo);

                    } else {
                        // TODO: FALTAN LOS OTROS CODE
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_422) + " al cargar los detalles", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectivoResponse> call, Throwable t) {
                Log.e("GestDirect", "onFailure: ", t);
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                hideDialog();
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etLastName1 = findViewById(R.id.etName1);
        etLastName2 = findViewById(R.id.etName2);
        etUserName = findViewById(R.id.etUserName);
        etPassword1 = findViewById(R.id.etPasword);
        etPassword2 = findViewById(R.id.etPasword2);
        etCargo = findViewById(R.id.etCargo);

        input_name = findViewById(R.id.inputName);
        input_lastName1 = findViewById(R.id.inputName1);
        input_lastName2 = findViewById(R.id.inputName2);
        input_userName = findViewById(R.id.inputUserName);
        input_password = findViewById(R.id.inputPasword);
        input_password2 = findViewById(R.id.inputPasword2);
        input_Cargo = findViewById(R.id.inputCargo);

        btnSend = findViewById(R.id.btnSend);

        pass1 = findViewById(R.id.linear_pass1);
        pass2 = findViewById(R.id.linear_pass2);

        inputValidations = new InputValidations(getApplicationContext());
    }

    private void initDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    private void showDialog(String msg) {
        pDialog.setMessage(msg);
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                verifyData();
                break;
        }
    }

    private void verifyData() {
        //validate is empty
        if (!inputValidations.isInputEditTextFilled(etName, input_name, getString(R.string.empty))) {
            etName.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etLastName1, input_lastName1, getString(R.string.empty))) {
            etLastName1.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etLastName2, input_lastName2, getString(R.string.empty))) {
            etLastName2.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etUserName, input_userName, getString(R.string.empty))) {
            etUserName.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etPassword1, input_password, getString(R.string.empty))) {
            etPassword1.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etPassword2, input_password2, getString(R.string.empty))) {
            etPassword2.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etCargo, input_Cargo, getString(R.string.empty))) {
            etCargo.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextLength(etPassword1, input_password, getString(R.string.password_length), 8)) {
            etPassword1.requestFocus();
            return;//validate password length
        } else if (!inputValidations.isInputEditTextMatches(etPassword1, etPassword2, input_password2, getString(R.string.password_matched))) {
            etPassword2.requestFocus();
            inputValidations.emptyInputEditTexts(etPassword2);
            return;//validate password matched
        }
        if (!inputValidations.isInputEditTextName(etName, input_name, getString(R.string.name_invalido))) {
            etName.requestFocus();
            return;
        }
        if (!inputValidations.isInputEditTextName(etLastName1, input_lastName1, getString(R.string.name_invalido))) {
            etLastName1.requestFocus();
            return;
        }
        if (!inputValidations.isInputEditTextName(etLastName2, input_lastName2, getString(R.string.name_invalido))) {
            etLastName2.requestFocus();
            return;
        } else {
            //get data
            String name = etName.getText().toString().trim();
            String lastName1 = etLastName1.getText().toString().trim();
            String lastName2 = etLastName2.getText().toString().trim();
            String userName = etUserName.getText().toString().trim();
            String password = etPassword1.getText().toString().trim();
            String cargo = etCargo.getText().toString().trim();

            if (getIntent().hasExtra(EXTRA_ID)) {
                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    updateData(id, name, lastName1, lastName2, userName, password, cargo);
                }
            } else {
                submitData(name, lastName1, lastName2, userName, password, cargo);
            }

        }
    }

    private void updateData(int id, String name, String lastName1, String lastName2, String userName, String password, String cargo) {
        showDialog(getString(R.string.enviando));

        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateDirectivoByDirectivo(id, name, lastName1, lastName2, userName, passwordEncrypted, cargo);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {

                        FancyToast.makeText(getApplicationContext(), getString(R.string.update), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent();
                        int id = getIntent().getIntExtra(EXTRA_ID, -1);
                        if (id != -1) {
                            intent.putExtra(EXTRA_ID, id);
                        }
                        setResult(RESULT_OK, intent);
                        finish();

                    } else if (response.code() == 421) {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_421), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    } else {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_422) + " al actualizar los datos", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("GestDirect", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });


    }

    private void submitData(String name, String lastName1, String lastName2, String userName, String password, String cargo) {
        showDialog(getString(R.string.enviando));
        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<UsuarioDirectivoCrearResponse> call = RetrofitClient.getInstance().getApi().crearDirectivoByDirectivo(name, lastName1, lastName2, userName, passwordEncrypted, cargo);
        call.enqueue(new Callback<UsuarioDirectivoCrearResponse>() {
            @Override
            public void onResponse(Call<UsuarioDirectivoCrearResponse> call, Response<UsuarioDirectivoCrearResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        FancyToast.makeText(getApplicationContext(), "Directivo " + getString(R.string.code_201), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if (response.code() == 421) {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_421), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    }
                } else if (response.code() == 421) {
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_421), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else {
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDirectivoCrearResponse> call, Throwable t) {
                Log.e("GestDirect", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().hasExtra(EXTRA_NOMBRE)) {
            getMenuInflater().inflate(R.menu.menu_gest, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_main) {
            showDialogConfirm();
            return true;
        }
        if (id == R.id.action_login_main) {
            startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        if (AppPreferences.getIdUsuario(getApplicationContext()) == ID) {
            FancyToast.makeText(getApplicationContext(), "No puedes eliminar tu propia cuenta", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        } else {
            showDialog(getString(R.string.delete));
            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteDirectivo(ID);
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            FancyToast.makeText(getApplicationContext(), "Directivo " + getString(R.string.eliminado), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            Intent intent = new Intent();
                            int id = getIntent().getIntExtra(EXTRA_ID, -1);
                            if (id != -1) {
                                intent.putExtra(EXTRA_ID, id);
                            }
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Log.e("GestDirect", "onFailure: ", t);
                    hideDialog();
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }
    }

    private void showDialogConfirm() {
        new AlertDialog.Builder(GestionarDirectivoActivity.this)
                .setIcon(R.drawable.ic_warning_white_24dp)
                .setTitle(getString(R.string.dialog_delete_tile))
                .setMessage(getString(R.string.dialog_delete_msg) + " " + getIntent().getStringExtra(EXTRA_NOMBRE) + " " + getIntent().getStringExtra(EXTRA_APELLIDO1) + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
