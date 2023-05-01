package com.gmail.orlandroyd.ohcc.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.helper.InputValidations;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioCiudadanoCrearResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.gmail.orlandroyd.ohcc.util.AESUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by OrlanDroyd on 16/04/2019.
 */
public class AccountEditFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextInputEditText etName, etLastName1, etLastName2, etUserName, etPassword1, etPassword2;
    private TextInputEditText etCi, etDirPart, etTelef, etEmail;
    private TextInputEditText etCategoria;
    private TextInputEditText etCargo;

    private TextInputLayout input_name, input_lastName1, input_lastName2, input_userName, input_password, input_password2;
    private TextInputLayout input_Ci, input_DirPart, input_Telef, input_Email;
    private TextInputLayout input_Cargo;
    private TextInputLayout input_Categoria;

    private CardView btnRegister;

    private int idRol, idUsuario;
    private LinearLayout layout_model_ciudadano, layout_model_especialista, layout_model_directivo;

    private ProgressDialog pDialog;
    private InputValidations inputValidations;
    private Spinner spinner;

    private String name;
    private String lastName1;
    private String lastName2;
    private String userName;
    private String password;

    private String ci;
    private String dirPart;
    private String telef;
    private String email;
    private int tipo;

    private String cargo;

    private String categoria;

    private OnEditFormActivityListener performLoginEditActivityListener;

    public interface OnEditFormActivityListener {

        void performLoginEdit(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol);

    }

    public AccountEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_account, container, false);

        initView();
        initListeners();
        initObjects();
        initDialog();

        return rootView;
    }

    private void initView() {
        idRol = AppPreferences.getRol(getContext());
        idUsuario = AppPreferences.getIdUsuario(getContext());
        name = AppPreferences.getNombre(getContext());
        lastName1 = AppPreferences.getApellido1(getContext());
        lastName2 = AppPreferences.getApellido2(getContext());
        userName = AppPreferences.getNombreUsuario(getContext());

        // Usuario
        etName = rootView.findViewById(R.id.etName);
        etLastName1 = rootView.findViewById(R.id.etName1);
        etLastName2 = rootView.findViewById(R.id.etName2);
        etUserName = rootView.findViewById(R.id.etUserName);
        etPassword1 = rootView.findViewById(R.id.etPasword);
        etPassword2 = rootView.findViewById(R.id.etPasword2);

        input_name = rootView.findViewById(R.id.inputName);
        input_lastName1 = rootView.findViewById(R.id.inputName1);
        input_lastName2 = rootView.findViewById(R.id.inputName2);
        input_userName = rootView.findViewById(R.id.inputUserName);
        input_password = rootView.findViewById(R.id.inputPasword);
        input_password2 = rootView.findViewById(R.id.inputPasword2);

        // Ciudadano
        etCi = rootView.findViewById(R.id.etCi);
        etDirPart = rootView.findViewById(R.id.etDirParticular);
        etTelef = rootView.findViewById(R.id.etTelef);
        etEmail = rootView.findViewById(R.id.etEmail);

        input_Ci = rootView.findViewById(R.id.inputCi);
        input_DirPart = rootView.findViewById(R.id.inputDirParticular);
        input_Telef = rootView.findViewById(R.id.inputTelef);
        input_Email = rootView.findViewById(R.id.inputEmail);

        // Especialista
        etCategoria = rootView.findViewById(R.id.etCategoria);
        input_Categoria = rootView.findViewById(R.id.inputCategoria);

        // Directivo
        etCargo = rootView.findViewById(R.id.etCargo);
        input_Cargo = rootView.findViewById(R.id.inputCargo);

        // btn
        btnRegister = rootView.findViewById(R.id.btnRegister);

        spinner = rootView.findViewById(R.id.spinner);

        layout_model_ciudadano = rootView.findViewById(R.id.layout_model_ciudadano);
        layout_model_especialista = rootView.findViewById(R.id.layout_model_especialista);
        layout_model_directivo = rootView.findViewById(R.id.layout_model_directivo);

        etName.setText(name);
        etLastName1.setText(lastName1);
        etLastName2.setText(lastName2);
        etUserName.setText(userName);

        switch (idRol) {
            case 1:
                layout_model_directivo.setVisibility(View.VISIBLE);
                updateViewDirectivo();
                break;
            case 2:
                layout_model_especialista.setVisibility(View.VISIBLE);
                updateViewEspecialista();
                break;
            case 3:
                layout_model_ciudadano.setVisibility(View.VISIBLE);
                updateViewCiudadano();
                break;
        }
    }

    private void updateViewCiudadano() {
        ci = AppPreferences.getCi(getContext());
        dirPart = AppPreferences.getDirPart(getContext());
        telef = AppPreferences.getTelef(getContext());
        email = AppPreferences.getEmail(getContext());

        etCi.setText(ci);
        etDirPart.setText(dirPart);
        etTelef.setText(telef);
        etEmail.setText(email);

    }

    private void updateViewEspecialista() {
        categoria = AppPreferences.getCategoria(getContext());
    }

    private void updateViewDirectivo() {
        cargo = AppPreferences.getCargo(getContext());
    }

    private void initObjects() {
        inputValidations = new InputValidations(rootView.getContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.login_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initListeners() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                verifyData();
        }
    }

    private void verifyDataCiudadano() {
        if (!inputValidations.isInputEditTextFilled(etCi, input_Ci, getString(R.string.empty))) {
            etCi.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etDirPart, input_DirPart, getString(R.string.empty))) {
            etDirPart.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etTelef, input_Telef, getString(R.string.empty))) {
            etTelef.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etEmail, input_Email, getString(R.string.empty))) {
            etEmail.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextEmail(etEmail, input_Email, getString(R.string.email_valid))) {
            etEmail.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextLength(etCi, input_Ci, getString(R.string.ci_length), 11)) {
            etCi.requestFocus();
            return;
        }
        /**
         * PHONE
         */
        else if (!inputValidations.isInputEditTextPhone(etTelef, input_Telef, getString(R.string.phone_invalido))) {
            etTelef.requestFocus();
            return;
        } else {
            ci = etCi.getText().toString().trim();
            dirPart = etDirPart.getText().toString().trim();
            telef = etTelef.getText().toString().trim();
            email = etEmail.getText().toString().trim();
            tipo = spinner.getSelectedItemPosition() + 1;
            submitDataCiudadano();
        }
    }

    private void verifyDataEspecialista() {
        if (!inputValidations.isInputEditTextFilled(etCategoria, input_Categoria, getString(R.string.empty))) {
            etCategoria.requestFocus();
            return;
        } else {
            categoria = etCategoria.getText().toString().trim();
            submitDataEspecialista();
        }
    }

    private void verifyDataDirectivo() {
        if (!inputValidations.isInputEditTextFilled(etCargo, input_Cargo, getString(R.string.empty))) {
            etCargo.requestFocus();
            return;
        } else {
            cargo = etCargo.getText().toString().trim();
            submitDataDirectivo();
        }
    }


    private void verifyData() {
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
        } else if (!inputValidations.isInputEditTextLength(etPassword1, input_password, getString(R.string.password_length), 8)) {
            etPassword1.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextMatches(etPassword1, etPassword2, input_password2, getString(R.string.password_matched))) {
            etPassword2.requestFocus();
            inputValidations.emptyInputEditTexts(etPassword2);
            return;
        }
//        else if (!inputValidations.isInputEditTextName(etName, input_name, getString(R.string.no_numeros))) {
//            etName.requestFocus();
//            return;//validate name not number
//        } else if (!inputValidations.isInputEditTextName(etLastName1, input_lastName1, getString(R.string.no_numeros))) {
//            etLastName1.requestFocus();
//            return;//validate lastname1 not number
//        } else if (!inputValidations.isInputEditTextName(etLastName2, input_lastName2, getString(R.string.no_numeros))) {
//            etLastName2.requestFocus();
//            return;//validate lastname2 not number
//        }
        else {

            name = etName.getText().toString().trim();
            lastName1 = etLastName1.getText().toString().trim();
            lastName2 = etLastName2.getText().toString().trim();
            userName = etUserName.getText().toString().trim();
            password = etPassword1.getText().toString().trim();

            inputValidations.hideKeyboardFrom(btnRegister);

            switch (idRol) {
                case 1:
                    verifyDataDirectivo();
                    break;
                case 2:
                    verifyDataEspecialista();
                    break;
                case 3:
                    verifyDataCiudadano();
                    break;
            }
        }
    }

    private void submitDataCiudadano() {
        showDialog();
        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateCiudadano(idUsuario, name, lastName1, lastName2, userName, passwordEncrypted, ci, dirPart, telef, email, tipo);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        FancyToast.makeText(getContext(), getString(R.string.update), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        performLoginEditActivityListener.performLoginEdit(idUsuario, name, lastName1, lastName2, userName, idRol);
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                } else {
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("AccountEditFragmet", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void submitDataEspecialista() {
        showDialog();
        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateEspecialistaByDirectivo(idUsuario, name, lastName1, lastName2, userName, passwordEncrypted, categoria);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        FancyToast.makeText(getContext(), getString(R.string.update), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        performLoginEditActivityListener.performLoginEdit(idUsuario, name, lastName1, lastName2, userName, idRol);
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                } else {
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("AccountEditFragmet", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void submitDataDirectivo() {
        showDialog();
        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateDirectivoByDirectivo(idUsuario, name, lastName1, lastName2, userName, passwordEncrypted, cargo);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        FancyToast.makeText(getContext(), getString(R.string.update), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        performLoginEditActivityListener.performLoginEdit(idUsuario, name, lastName1, lastName2, userName, idRol);
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                } else {
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("AccountEditFragmet", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        performLoginEditActivityListener = (OnEditFormActivityListener) activity;
    }

}