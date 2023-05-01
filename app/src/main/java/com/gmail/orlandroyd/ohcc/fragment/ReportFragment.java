package com.gmail.orlandroyd.ohcc.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.AuthenticationActivity;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.model.JoinCiudadano;
import com.gmail.orlandroyd.ohcc.networking.api.model.JoinDirectivo;
import com.gmail.orlandroyd.ohcc.networking.api.model.JoinEspecialista;
import com.gmail.orlandroyd.ohcc.networking.api.model.Solicitud;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinCiudadanoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinDirectivoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinEspecialistaResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudGetAllResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.gmail.orlandroyd.ohcc.report.TemplatePDF;
import com.itextpdf.text.DocumentException;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class ReportFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private CardView card1, card2, card3, card4, card5;

    /**
     * PDF
     */
    private TemplatePDF templatePDF;
    private String fileName = "Reporte-" + getFechaHoy();
    private String author;
    private ProgressDialog pDialog;
    private List<JoinCiudadano> itemsCiudadano;
    private List<JoinEspecialista> itemsEspecialista;
    private List<JoinDirectivo> itemsDirectivo;
    private List<Solicitud> itemsSolicitud;
    private String title = "Reporte";
    private int type;
    String[] header;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_report, container, false);

        author = AppPreferences.getNombre(getContext()) + " " + AppPreferences.getApellido1(getContext()) + " " + AppPreferences.getApellido2(getContext());
        initDialog();

        card1 = rootView.findViewById(R.id.card1_report);
        card2 = rootView.findViewById(R.id.card2_report);
        card3 = rootView.findViewById(R.id.card3_report);
        card4 = rootView.findViewById(R.id.card4_report);
        card5 = rootView.findViewById(R.id.card5_report);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.card1_report:
                header = new String[]{"No.", "Nombre", "Primer Apellido", "Segundo Apellido", "Nombre de Usuario", "CI", "Dir. Particular", "Telef", "E-mail"};
                type = 1;
                title = "Usuarios";
                fileName = "Usuarios-" + getFechaHoy();
                getDataCiudadano();
                break;
            case R.id.card2_report:
                header = new String[]{"No.", "Nombre", "Primer Apellido", "Segundo Apellido", "Nombre de Usuario", "Categoría"};
                type = 2;
                title = "Especialistas";
                fileName = "Especialistas-" + getFechaHoy();
                getDataEspecialista();
                break;
            case R.id.card3_report:
                header = new String[]{"No.", "Nombre", "Primer Apellido", "Segundo Apellido", "Nombre de Usuario", "Cargo"};
                type = 3;
                title = "Directivos";
                fileName = "Directivos-" + getFechaHoy();
                getDataDirectivos();
                break;
            case R.id.card4_report:
                header = new String[]{"No.", "No. Usuario:", "Fecha creada:", "Sugerencia:", "Respuesta:"};
                type = 4;
                title = "Sugerencias";
                fileName = "Sugerencias-" + getFechaHoy();
                getDataSolicitud(6);
                break;
            case R.id.card5_report:
                header = new String[]{"No.", "No. Usuario:", "Fecha creada:", "Queja:", "Respuesta:"};
                type = 5;
                title = "Quejas";
                fileName = "Quejas-" + getFechaHoy();
                getDataSolicitud(5);
                break;

        }
    }

    private void getDataSolicitud(int tipoSolicitud) {
        showDialog();
        Call<SolicitudGetAllResponse> call = RetrofitClient.getInstance().getApi().getSolicitudesFinalizadas(tipoSolicitud);
        call.enqueue(new Callback<SolicitudGetAllResponse>() {
            @Override
            public void onResponse(Call<SolicitudGetAllResponse> call, Response<SolicitudGetAllResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        itemsSolicitud = response.body().getData();
                        createPdfWrapper();
                    } else if (response.code() == 206) {
                        hideDialog();
                        FancyToast.makeText(getContext(), "No hay datos que mostrar", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    } else {
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    hideDialog();
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<SolicitudGetAllResponse> call, Throwable t) {
                Log.e("RepoFragment", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void getDataDirectivos() {
        showDialog();
        Call<JoinDirectivoResponse> call = RetrofitClient.getInstance().getApi().getDirectivos();
        call.enqueue(new Callback<JoinDirectivoResponse>() {
            @Override
            public void onResponse(Call<JoinDirectivoResponse> call, Response<JoinDirectivoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        itemsDirectivo = response.body().getData();
                        createPdfWrapper();
                    } else if (response.code() == 206) {
                        hideDialog();
                        FancyToast.makeText(getContext(), "No hay datos que mostrar", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    } else {
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    hideDialog();
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<JoinDirectivoResponse> call, Throwable t) {
                Log.e("RepoFragment", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void getDataEspecialista() {
        showDialog();
        Call<JoinEspecialistaResponse> call = RetrofitClient.getInstance().getApi().getEspecialistas();
        call.enqueue(new Callback<JoinEspecialistaResponse>() {
            @Override
            public void onResponse(Call<JoinEspecialistaResponse> call, Response<JoinEspecialistaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        itemsEspecialista = response.body().getData();
                        createPdfWrapper();
                    } else if (response.code() == 206) {
                        hideDialog();
                        FancyToast.makeText(getContext(), "No hay datos que mostrar", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    } else {
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    hideDialog();
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<JoinEspecialistaResponse> call, Throwable t) {
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void getDataCiudadano() {
        showDialog();
        Call<JoinCiudadanoResponse> call = RetrofitClient.getInstance().getApi().getCiudadanos();
        call.enqueue(new Callback<JoinCiudadanoResponse>() {
            @Override
            public void onResponse(Call<JoinCiudadanoResponse> call, Response<JoinCiudadanoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        itemsCiudadano = response.body().getData();
                        createPdfWrapper();
                    } else if (response.code() == 206) {
                        hideDialog();
                        FancyToast.makeText(getContext(), "No hay datos que mostrar", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    } else {
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                    }
                } else {
                    hideDialog();
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<JoinCiudadanoResponse> call, Throwable t) {
                Log.e("RepoFragment", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login_main) {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * PERMISSIONS
     */
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    createPdfWrapper();
                } else {
                    // Permission Denied
                    FancyToast.makeText(getContext(), "WRITE_EXTERNAL Permission Denegado...", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Crear PDF pidiendo permiso de escritura
     */
    private void createPdfWrapper() {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("Para crear el pdf, necesita dar permisos de escritura",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            ReportFragment.CreatePdfAsyncTask task = new ReportFragment.CreatePdfAsyncTask(this);
            task.execute();
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    /**
     * PDF
     */
    private String getFechaHoy() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(new Date());
    }

    private boolean setUpPdf() throws DocumentException, IOException {
        if ((itemsCiudadano == null || itemsCiudadano.size() == 0) && (itemsEspecialista == null || itemsEspecialista.size() == 0) && (itemsDirectivo == null || itemsDirectivo.size() == 0) && (itemsSolicitud == null || itemsSolicitud.size() == 0)) {
            return false;
        } else {
            String[] metaData = {"Reportes-OHCC", "APK", author};
            String subtitle = "Creado por: " + author;
            templatePDF = new TemplatePDF(getContext());
            templatePDF.openDocument(fileName);
            templatePDF.addMetaData(metaData[0], metaData[1], metaData[2]);
//            templatePDF.addLogo();
            templatePDF.addTitles(title, subtitle, getFechaHoy());
            ArrayList<String[]> data = new ArrayList<>();
            switch (type) {
                case 1: // Ciudadano
                    data = getArrayCiudadano();
                    break;
                case 2: // Especialista
                    data = getArrayEspecialista();
                    break;
                case 3: // Directivo
                    data = getArrayDirectivo();
                    break;
                case 4: // Sugerencia
                    data = getArraySolicitud();
                case 5: // Queja
                    data = getArraySolicitud();
            }
            templatePDF.createTable(header, data);
            templatePDF.closeDocument();
            setListsNull();
            return true;
        }
    }

    private void setListsNull() {
        itemsSolicitud = null;
        itemsDirectivo = null;
        itemsEspecialista = null;
        itemsCiudadano = null;
    }


    private ArrayList<String[]> getArrayCiudadano() {
        ArrayList<String[]> row = new ArrayList<>();
        for (int i = 0; i < itemsCiudadano.size(); i++) {
            row.add(new String[]{"" + (i + 1), itemsCiudadano.get(i).getName(), itemsCiudadano.get(i).getLastName1(), itemsCiudadano.get(i).getLastName2(), itemsCiudadano.get(i).getUser_name(), itemsCiudadano.get(i).getCi(), itemsCiudadano.get(i).getDir_particular(), itemsCiudadano.get(i).getTelef(), itemsCiudadano.get(i).getEmail()});
        }
        return row;
    }

    private ArrayList<String[]> getArrayEspecialista() {
        ArrayList<String[]> row = new ArrayList<>();
        for (int i = 0; i < itemsEspecialista.size(); i++) {
            row.add(new String[]{"" + (i + 1), itemsEspecialista.get(i).getName(), itemsEspecialista.get(i).getLastName1(), itemsEspecialista.get(i).getLastName2(), itemsEspecialista.get(i).getUser_name(), itemsEspecialista.get(i).getCategoria()});
        }
        return row;
    }

    private ArrayList<String[]> getArrayDirectivo() {
        ArrayList<String[]> row = new ArrayList<>();
        for (int i = 0; i < itemsDirectivo.size(); i++) {
            row.add(new String[]{"" + (i + 1), itemsDirectivo.get(i).getName(), itemsDirectivo.get(i).getLastName1(), itemsDirectivo.get(i).getLastName2(), itemsDirectivo.get(i).getUser_name(), itemsDirectivo.get(i).getCargo()});
        }
        return row;
    }

    private ArrayList<String[]> getArraySolicitud() {
        ArrayList<String[]> row = new ArrayList<>();
        for (int i = 0; i < itemsSolicitud.size(); i++) {
            row.add(new String[]{"" + (i + 1), "" + itemsSolicitud.get(i).getId_usuario(), getFecha(itemsSolicitud.get(i).getFecha_creada()), itemsSolicitud.get(i).getDescripcion(), itemsSolicitud.get(i).getRespuesta()});
        }
        return row;
    }

    private String getFecha(String fecha_creada) {
        String anio = "" + fecha_creada.charAt(0) + fecha_creada.charAt(1) + fecha_creada.charAt(2) + fecha_creada.charAt(3);
        String mes = "" + fecha_creada.charAt(5) + fecha_creada.charAt(6);
        String dia = "" + fecha_creada.charAt(8) + fecha_creada.charAt(9);
        return dia + "/" + mes + "/" + anio;
    }

    private static class CreatePdfAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<ReportFragment> fragmentWeakReference;
        private String msg;
        private boolean resoult;

        public CreatePdfAsyncTask(ReportFragment fragment) {
            fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ReportFragment activity = fragmentWeakReference.get();
            if (activity == null || activity.getActivity().isFinishing()) {
                return;
            }
            activity.showDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ReportFragment activity = fragmentWeakReference.get();
            try {
                if (activity == null || activity.getActivity().isFinishing()) {
                    return null;
                }
                resoult = activity.setUpPdf();
                if (!resoult) {
                    msg = "No hay registros que guardar";
                }

            } catch (DocumentException e) {
                e.printStackTrace();
                msg = "Hubo un problema creando el pdf";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ReportFragment activity = fragmentWeakReference.get();
            if (activity == null || activity.getActivity().isFinishing()) {
                return;
            }
            activity.hideDialog();
            if (resoult) {
                String msgOk = "Guardado en: " + Environment.getExternalStorageDirectory().toString() + "/ReportesOHCC/" + activity.fileName;
                FancyToast.makeText(activity.getContext(), msgOk, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                activity.templatePDF.appViewPdf(activity.getActivity());
            }
            if (msg != null) {
                FancyToast.makeText(activity.getContext(), msg, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        }
    }

    private void initDialog() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
