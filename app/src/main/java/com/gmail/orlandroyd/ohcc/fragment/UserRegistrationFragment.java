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
import android.widget.Spinner;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.helper.InputValidations;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
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
public class UserRegistrationFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextInputEditText etName, etLastName1, etLastName2, etUserName, etPassword1, etPassword2, etCi, etDirPart, etTelef, etEmail;
    private TextInputLayout input_name, input_lastName1, input_lastName2, input_userName, input_password, input_password2, input_Ci, input_DirPart, input_Telef, input_Email;
    private CardView btnRegister;
    private OnRegistrationListener registrationListener;
    private ProgressDialog pDialog;
    private InputValidations inputValidations;
    private Spinner spinner;

    public interface OnRegistrationListener {
        void registrationPerformed(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol, String ci, String dir_part, String telef, String email, int tipo_ciudadano);
    }

    public UserRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_registration, container, false);

        initView();
        initListeners();
        initObjects();
        initDialog();

        return rootView;
    }

    private void initView() {
        etName = rootView.findViewById(R.id.etName);
        etLastName1 = rootView.findViewById(R.id.etName1);
        etLastName2 = rootView.findViewById(R.id.etName2);
        etUserName = rootView.findViewById(R.id.etUserName);
        etPassword1 = rootView.findViewById(R.id.etPasword);
        etPassword2 = rootView.findViewById(R.id.etPasword2);
        etCi = rootView.findViewById(R.id.etCi);
        etDirPart = rootView.findViewById(R.id.etDirParticular);
        etTelef = rootView.findViewById(R.id.etTelef);
        etEmail = rootView.findViewById(R.id.etEmail);

        input_name = rootView.findViewById(R.id.inputName);
        input_lastName1 = rootView.findViewById(R.id.inputName1);
        input_lastName2 = rootView.findViewById(R.id.inputName2);
        input_userName = rootView.findViewById(R.id.inputUserName);
        input_password = rootView.findViewById(R.id.inputPasword);
        input_password2 = rootView.findViewById(R.id.inputPasword2);
        input_Ci = rootView.findViewById(R.id.inputCi);
        input_DirPart = rootView.findViewById(R.id.inputDirParticular);
        input_Telef = rootView.findViewById(R.id.inputTelef);
        input_Email = rootView.findViewById(R.id.inputEmail);

        btnRegister = rootView.findViewById(R.id.btnRegister);

        spinner = rootView.findViewById(R.id.spinner);

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
        } else if (!inputValidations.isInputEditTextFilled(etCi, input_Ci, getString(R.string.empty))) {
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
        }
        /**
         * PHONE
         */
        else if (!inputValidations.isInputEditTextPhone(etTelef, input_Telef, getString(R.string.phone_invalido))) {
            etTelef.requestFocus();
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
        else if (!inputValidations.isInputEditTextEmail(etEmail, input_Email, getString(R.string.email_valid))) {
            etEmail.requestFocus();
            return;//validate email
        } else if (!inputValidations.isInputEditTextLength(etPassword1, input_password, getString(R.string.password_length), 8)) {
            etPassword1.requestFocus();
            return;//validate password length
        } else if (!inputValidations.isInputEditTextLength(etCi, input_Ci, getString(R.string.ci_length), 11)) {
            etCi.requestFocus();
            return;//validate ci length
        } else if (!inputValidations.isInputEditTextMatches(etPassword1, etPassword2, input_password2, getString(R.string.password_matched))) {
            etPassword2.requestFocus();
            inputValidations.emptyInputEditTexts(etPassword2);
            return;//validate password matched
        } else {
            //get data
            String name = etName.getText().toString().trim();
            String lastName1 = etLastName1.getText().toString().trim();
            String lastName2 = etLastName2.getText().toString().trim();
            String userName = etUserName.getText().toString().trim();
            String password = etPassword1.getText().toString().trim();
            String ci = etCi.getText().toString().trim();
            String dirPart = etDirPart.getText().toString().trim();
            String telef = etTelef.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            int tipo = spinner.getSelectedItemPosition() + 1;
            // hide key
            inputValidations.hideKeyboardFrom(btnRegister);
            //submit
            submitData(name, lastName1, lastName2, userName, password, ci, dirPart, telef, email, tipo);
        }
    }

    private void submitData(String name, String lastName1, String lastName2, String userName, String password, String ci, String dirPart, String telef, String email, int tipo) {
        showDialog();
        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<UsuarioCiudadanoCrearResponse> call = RetrofitClient.getInstance().getApi().crearCiudadano(name, lastName1, lastName2, userName, passwordEncrypted, 3, ci, dirPart, telef, email, tipo);
        call.enqueue(new Callback<UsuarioCiudadanoCrearResponse>() {
            @Override
            public void onResponse(Call<UsuarioCiudadanoCrearResponse> call, Response<UsuarioCiudadanoCrearResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.code() == 201) {
                            UsuarioCiudadanoCrearResponse usuarioCiudadanoCrearResponse = response.body();
                            // Save login status
                            AppPreferences.saveLoginStatus(true, getContext());
                            // Usuario
                            int idUsuario = usuarioCiudadanoCrearResponse.getData_usuario().getId_usuario();
                            String nombre = usuarioCiudadanoCrearResponse.getData_usuario().getName();
                            String apellido1 = usuarioCiudadanoCrearResponse.getData_usuario().getLastName1();
                            String apellido2 = usuarioCiudadanoCrearResponse.getData_usuario().getLastName2();
                            String nombreUsuario = usuarioCiudadanoCrearResponse.getData_usuario().getUser_name();
                            String password = usuarioCiudadanoCrearResponse.getData_usuario().getPassword();
                            // Ciudadano
                            int idRol = usuarioCiudadanoCrearResponse.getData_usuario().getId_rol();
                            String ci = usuarioCiudadanoCrearResponse.getData_ciudadano().getCi();
                            String dir_part = usuarioCiudadanoCrearResponse.getData_ciudadano().getDir_particular();
                            String telef = usuarioCiudadanoCrearResponse.getData_ciudadano().getTelef();
                            String email = usuarioCiudadanoCrearResponse.getData_ciudadano().getEmail();
                            int id_tipo_ciudadano = usuarioCiudadanoCrearResponse.getData_ciudadano().getId_tipo_ciudadano();
                            // Save
                            registrationListener.registrationPerformed(idUsuario, nombre, apellido1, apellido2, nombreUsuario, idRol, ci, dir_part, telef, email, id_tipo_ciudadano);
                            FancyToast.makeText(getContext(), "Usuario " + getString(R.string.code_201), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        } else if (response.code() == 421) {
                            FancyToast.makeText(getContext(), getString(R.string.code_421), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        } else {
                            FancyToast.makeText(getContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                } else {
                    if (response.code() == 421) {
                        FancyToast.makeText(getContext(), getString(R.string.code_421), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UsuarioCiudadanoCrearResponse> call, Throwable t) {
                Log.e("UserRegistration", "onFailure: ", t);
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                hideDialog();
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
        registrationListener = (OnRegistrationListener) activity;
    }
}