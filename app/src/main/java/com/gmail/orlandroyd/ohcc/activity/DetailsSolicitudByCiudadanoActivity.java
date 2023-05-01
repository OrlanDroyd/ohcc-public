package com.gmail.orlandroyd.ohcc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codesgood.views.JustifiedTextView;
import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsSolicitudByCiudadanoActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID_SOLICITUD";
    public static final String EXTRA_EVALUACION = "EXTRA_EVALUACION";
    public static final String EXTRA_RESPUESTA = "EXTRA_RESPUESTA";
    public static final String EXTRA_FECHA_CREADA = "EXTRA_FECHA_CREADA";
    public static final String EXTRA_FECHA_INICIADA = "EXTRA_FECHA_INICIADA";
    public static final String EXTRA_FECHA_RESPUESTA = "EXTRA_FECHA_RESPUESTA";
    public static final String EXTRA_DESCIPCION = "EXTRA_DESCIPCION";
    public static final String EXTRA_ID_USUARIO = "EXTRA_ID_USUARIO";
    public static final String EXTRA_ID_ESPECIALISTA = "EXTRA_ID_ESPECIALISTA";
    public static final String EXTRA_ID_TIPO_SOLICITUD = "EXTRA_ID_TIPO_SOLICITUD";
    public static final String EXTRA_ID_ESTADO = "EXTRA_ID_ESTADO";

    private ProgressDialog pDialog;

    private AppCompatRatingBar ratingBar;

    private int idSolicitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_solicitud_by_ciudadano);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setTitle("Detalles");

        idSolicitud = getIntent().getExtras().getInt(EXTRA_ID);

        ratingBar = findViewById(R.id.ratingBar);
        CardView btnSubmit = findViewById(R.id.btnSubmit);
        JustifiedTextView jTvDescripcion = findViewById(R.id.jTvDescripcion);
        JustifiedTextView jTvRespuesta = findViewById(R.id.jTvRespuesta);
        AppCompatTextView tvFechaCreada = findViewById(R.id.tvFechaCreada_ciudadano);
        AppCompatTextView tvFechaIniciada = findViewById(R.id.tvFechaIniciada_ciudadano);
        AppCompatTextView tvFechaFinalizada = findViewById(R.id.tvFechaFinalizada_ciudadano);
        AppCompatTextView tvIdEspecialista = findViewById(R.id.tvIdEspecialista_ciudadano);
        AppCompatTextView tvIdSolicitud = findViewById(R.id.tvIdSolicitud_ciudadano);
        AppCompatTextView tvEvaluacion = findViewById(R.id.tvEvaluacion_ciudadano);

        String descripcion = getIntent().getExtras().getString(EXTRA_DESCIPCION);
        String respuesta = getIntent().getExtras().getString(EXTRA_RESPUESTA);
        int evaluacion = getIntent().getExtras().getInt(EXTRA_EVALUACION);

        String fechaCreada = getIntent().getExtras().getString(EXTRA_FECHA_CREADA);
        if (fechaCreada != null) {
            tvFechaCreada.setText(getFecha(fechaCreada));
        }
        String fechaIniciada = getIntent().getExtras().getString(EXTRA_FECHA_INICIADA);
        if (fechaIniciada != null) {
            tvFechaIniciada.setText(getFecha(fechaIniciada));
        }
        String fechaFinalizada = getIntent().getExtras().getString(EXTRA_FECHA_RESPUESTA);
        if (fechaFinalizada != null) {
            tvFechaFinalizada.setText(getFecha(fechaFinalizada));
        }
        int idEspecialista = getIntent().getExtras().getInt(EXTRA_ID_ESPECIALISTA);
        int idSolicitud = getIntent().getExtras().getInt(EXTRA_ID);


        if (evaluacion != -1) {
            if (evaluacion < 3) {
                tvEvaluacion.setTextColor(getResources().getColor(R.color.colorM));
            } else if (evaluacion < 4) {
                tvEvaluacion.setTextColor(getResources().getColor(R.color.colorB));
            } else if (evaluacion >= 4) {
                tvEvaluacion.setTextColor(getResources().getColor(R.color.colorE));
            }
            ratingBar.setRating((float) evaluacion);
            tvEvaluacion.setText(String.valueOf(evaluacion));
        }


        jTvDescripcion.setText(descripcion);
        jTvRespuesta.setText(respuesta);
        tvIdEspecialista.setText(String.valueOf(idEspecialista));
        tvIdSolicitud.setText(String.valueOf(idSolicitud));

        initDialog();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluar();
            }
        });

    }

    private void evaluar() {
        showDialog(getResources().getString(R.string.enviando));
        int evaluacion = (int) ratingBar.getRating();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().setEvaluacion(idSolicitud, evaluacion);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        FancyToast.makeText(getApplicationContext(), getString(R.string.code_422) + " al enviar su evaluación.", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("Details", "onFailure: ", t);
                FancyToast.makeText(getApplicationContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                hideDialog();
            }
        });
    }

    private String getFecha(String fecha_creada) {
        String anio = "" + fecha_creada.charAt(0) + fecha_creada.charAt(1) + fecha_creada.charAt(2) + fecha_creada.charAt(3);
        String mes = "" + fecha_creada.charAt(5) + fecha_creada.charAt(6);
        String dia = "" + fecha_creada.charAt(8) + fecha_creada.charAt(9);
        return dia + "/" + mes + "/" + anio;
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
}
