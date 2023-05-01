package com.gmail.orlandroyd.ohcc.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.CiudadanoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.DirectivoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.EspecialistaResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OrlanDroyd on 16/04/2019.
 */
public class AccountFragment extends Fragment {
    private View rootView;
    private AppCompatTextView tvNombre, tvNombreUsuario, tvRol;
    private AppCompatTextView tvCategoria;
    private AppCompatTextView tvCargo;
    private AppCompatTextView tvCi, tvDirPart, tvTelef, tvEmail, tvTipoCiudadano;
    private CardView btnLogout;
    private AppCompatImageView btnMore;
    private LinearLayout layoutTipo;
    private ProgressDialog pDialog;
    private OnLogoutListener logoutListener;
    private int idRol, idUsuario;
    private LinearLayout layoutInfoDirectivo, layoutInfoEspecialista, layoutInfoCiudadano;

    // if the user click the button, the fragment need to communicate with
    // the AuthenticationActivity, and that is necessary make a interface
    public interface OnLogoutListener {
        void logoutPerformed();

        void editPerformed();
    }


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        tvNombre = rootView.findViewById(R.id.tvNombre_account);
        tvNombreUsuario = rootView.findViewById(R.id.tvNombreUsuario_account);
        tvRol = rootView.findViewById(R.id.tvTipo_account);
        btnLogout = rootView.findViewById(R.id.btnLogout_account);
        btnMore = rootView.findViewById(R.id.btnMore);
        layoutTipo = rootView.findViewById(R.id.layoutTipo_account);

        tvCargo = rootView.findViewById(R.id.tvCargo_account);
        tvCategoria = rootView.findViewById(R.id.tvCategoria_account);

        tvCi = rootView.findViewById(R.id.tvCi_account);
        tvDirPart = rootView.findViewById(R.id.tvDirPart_account);
        tvTelef = rootView.findViewById(R.id.tvTelef_account);
        tvEmail = rootView.findViewById(R.id.tvEmail_account);
        tvTipoCiudadano = rootView.findViewById(R.id.tvTipoCiudadano_account);

        layoutInfoEspecialista = rootView.findViewById(R.id.layoutInfoEspecialista);
        layoutInfoDirectivo = rootView.findViewById(R.id.layoutInfoDirectivos);
        layoutInfoCiudadano = rootView.findViewById(R.id.layoutInfoCiudadano);

        initDialog();

        idUsuario = AppPreferences.getIdUsuario(getContext());
        String nombre = AppPreferences.getNombre(getContext());
        String apellido1 = AppPreferences.getApellido1(getContext());
        String apellido2 = AppPreferences.getApellido2(getContext());
        String nombreUsuario = AppPreferences.getNombreUsuario(getContext());
        idRol = AppPreferences.getRol(getContext());

        String rol;
        switch (idRol) {
            case 1:
                rol = "Directivo";
                layoutTipo.setVisibility(View.VISIBLE);
                tvRol.setText(rol);
                break;
            case 2:
                rol = "Especialista";
                layoutTipo.setVisibility(View.VISIBLE);
                tvRol.setText(rol);
                break;
            case 4:
                rol = "Administrador";
                layoutTipo.setVisibility(View.VISIBLE);
                tvRol.setText(rol);
                break;
        }

        tvNombre.setText(nombre + " " + apellido1 + " " + apellido2);
        tvNombreUsuario.setText(nombreUsuario);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutListener.logoutPerformed();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMore();
            }
        });

        return rootView;
    }

    private void displayMore() {
        showDialog(getString(R.string.loading));
        switch (idRol) {
            case 1:
                Call<DirectivoResponse> callDirectivo = RetrofitClient.getInstance().getApi().getDirectivoByID(idUsuario);
                callDirectivo.enqueue(new Callback<DirectivoResponse>() {
                    @Override
                    public void onResponse(Call<DirectivoResponse> call, Response<DirectivoResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                String cargo = response.body().getData().getCargo();
                                layoutInfoDirectivo.setVisibility(View.VISIBLE);
                                tvCargo.setText(cargo);
                                btnMore.setVisibility(View.GONE);
                            } else {
                                FancyToast.makeText(getContext(), getString(R.string.code_422) + " y no se ha podido editar.", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectivoResponse> call, Throwable t) {
                        Log.e("AccountFragmet", "onFailure: ", t);
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_422) + " de conexión", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
                break;
            case 2:
                Call<EspecialistaResponse> callEspecialista = RetrofitClient.getInstance().getApi().getEspecialistaByID(idUsuario);
                callEspecialista.enqueue(new Callback<EspecialistaResponse>() {
                    @Override
                    public void onResponse(Call<EspecialistaResponse> call, Response<EspecialistaResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                String categoria = response.body().getData().getCategoria();
                                layoutInfoEspecialista.setVisibility(View.VISIBLE);
                                tvCategoria.setText(categoria);
                                btnMore.setVisibility(View.GONE);
                            } else {
                                FancyToast.makeText(getContext(), getString(R.string.code_422) + " y no se ha podido editar.", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EspecialistaResponse> call, Throwable t) {
                        Log.e("AccountFragmet", "onFailure: ", t);
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_4221) + " de conexión", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
                break;
            case 3:
                Call<CiudadanoResponse> callCiudadano = RetrofitClient.getInstance().getApi().getCiudadanoById(idUsuario);
                callCiudadano.enqueue(new Callback<CiudadanoResponse>() {
                    @Override
                    public void onResponse(Call<CiudadanoResponse> call, Response<CiudadanoResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                String ci = response.body().getData().getCi();
                                String dir_particular = response.body().getData().getDir_particular();
                                String telef = response.body().getData().getTelef();
                                String email = response.body().getData().getEmail();
                                int tipoCiudadano = response.body().getData().getId_tipo_ciudadano();

                                tvCi.setText(ci);
                                tvDirPart.setText(dir_particular);
                                tvTelef.setText(telef);
                                tvEmail.setText(email);

                                switch (tipoCiudadano) {
                                    case 1:
                                        tvTipoCiudadano.setText("Persona Natural");
                                        break;
                                    case 2:
                                        tvTipoCiudadano.setText("Trabajador por Cuenta Propia");
                                        break;
                                }

                                layoutInfoCiudadano.setVisibility(View.VISIBLE);
                                btnMore.setVisibility(View.GONE);
                            } else {
                                FancyToast.makeText(getContext(), getString(R.string.code_422) + " y no se ha podido editar.", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CiudadanoResponse> call, Throwable t) {
                        Log.e("AccountFragmet", "onFailure: ", t);
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_4221) + " de conexión", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
                hideDialog();
                break;
            case 4:
                hideDialog();
                FancyToast.makeText(getContext(), " No hay más información disponible ", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_acount, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_main) {
            editar();
            return true;
        }
        if (id == R.id.action_delete_main) {
            showDialogConfirm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogConfirm() {
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_warning_white_24dp)
                .setTitle(getString(R.string.dialog_delete_tile))
                .setMessage(getString(R.string.dialog_delete_account_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void eliminar() {
        showDialog(getString(R.string.delete));
        switch (idRol) {
            case 1:
                hideDialog();
                FancyToast.makeText(getContext(), "No puedes eliminar tu cuenta de Directivo", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                break;
            case 2:
                Call<DefaultResponse> callEspecialista = RetrofitClient.getInstance().getApi().deleteEspecialista(idUsuario);
                callEspecialista.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                logoutListener.logoutPerformed();
                                FancyToast.makeText(getContext(), getString(R.string.eliminado), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getContext(), "No puede eliminar su cuenta porque tiene relación con los trámites de los usuarios", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Log.e("AccountFragmet", "onFailure: ", t);
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
                break;
            case 3:
                Call<DefaultResponse> callCiudadano = RetrofitClient.getInstance().getApi().deleteCiudadano(idUsuario);
                callCiudadano.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                logoutListener.logoutPerformed();
                                FancyToast.makeText(getContext(), getString(R.string.eliminado), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getContext(), "No puede eliminar su cuenta porque ya ha hecho trámites con ella", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Log.e("AccountFragmet", "onFailure: ", t);
                        hideDialog();
                        FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
                break;

            case 4:
                hideDialog();
                FancyToast.makeText(getContext(), "No es posible eliminar al administrador", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                break;
        }
    }

    private void editar() {
        logoutListener.editPerformed();
    }

    private void initDialog() {
        pDialog = new ProgressDialog(getContext());
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;
    }
}
