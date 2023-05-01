package com.gmail.orlandroyd.ohcc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.helper.InputValidations;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudCrearResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionarSolicitudByEspecialistaActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatEditText etDescripcion;
    private CardView btnEnviar;
    private String fechaCreada, descripcion;
    private InputValidations inputValidations;
    private ProgressDialog pDialog;

    public static final String EXTRA_ID = "EXTRA_ID_SOLICITUD";
    public static final String EXTRA_DESCRIPCION = "EXTRA_DESCRIPCION_SOLICITUD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_gestionar_especialista);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        etDescripcion = findViewById(R.id.etDescripcion_solicitud);
        btnEnviar = findViewById(R.id.btnEnviar_solicitud);

        btnEnviar.setOnClickListener(this);

        inputValidations = new InputValidations(getApplicationContext());
        initDialog();

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            getSupportActionBar().setTitle("EDITAR");
            etDescripcion.setText(intent.getStringExtra(EXTRA_DESCRIPCION));
        } else {
            getSupportActionBar().setTitle("NUEVA");
        }


    }

    private void initDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.enviando));
        pDialog.setCancelable(false);
    }

    private void showDialog(String msg) {
        pDialog.setMessage(msg);
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }


    private String getFechaHoy() {
        return "2019-05-23";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviar_solicitud:
                verifyData();
                break;
        }
    }

    private void verifyData() {
        if (!inputValidations.isEditTextFilled(etDescripcion, getString(R.string.descripcion_requerida))) {
            return;
        } else {
            fechaCreada = getFechaHoy();
            descripcion = etDescripcion.getText().toString().trim();
            int idUsuario = AppPreferences.getIdUsuario(getApplicationContext());
            int tipo_solicitud = AppPreferences.getFragmetPos(getApplicationContext());

            if (getIntent().hasExtra(EXTRA_ID)) {
                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    fechaCreada = getFechaHoy();
                    updateData(id, fechaCreada, descripcion);
                }
            } else {
                submitData(fechaCreada, descripcion, idUsuario, tipo_solicitud);
            }

        }
    }

    private void updateData(int id, String fechaCreada, String descripcion) {
        showDialog(getString(R.string.enviando));
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateSolicitudByUsuario(id, fechaCreada, descripcion);
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
                    } else {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("GestSolicitudEsp", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });

    }

    private void submitData(String fechaCreada, String descripcion, int idUsuario, int tipo_solicitud) {
        showDialog(getString(R.string.enviando));

        Call<SolicitudCrearResponse> call = RetrofitClient.getInstance().getApi().crearSolicitud(fechaCreada, descripcion, idUsuario, tipo_solicitud);
        call.enqueue(new Callback<SolicitudCrearResponse>() {
            @Override
            public void onResponse(Call<SolicitudCrearResponse> call, Response<SolicitudCrearResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_200), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent();
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
            public void onFailure(Call<SolicitudCrearResponse> call, Throwable t) {
                Log.e("GestSolicitudEsp", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

}
