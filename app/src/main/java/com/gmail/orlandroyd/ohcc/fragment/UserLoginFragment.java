package com.gmail.orlandroyd.ohcc.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.helper.InputValidations;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.response.LoginResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.gmail.orlandroyd.ohcc.util.AESUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OrlanDroyd on 16/04/2019.
 */
public class UserLoginFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private AppCompatTextView tvLogin;
    private TextInputEditText etUserName, etUserPassword;
    private TextInputLayout input_etUserName, input_etUserPassword;
    private CardView btnLogin;
    private OnLoginFormActivityListener loginFormActivityListener;
    private ProgressDialog pDialog;
    private InputValidations inputValidations;


    public interface OnLoginFormActivityListener {

        void performRegister();

        void performLogin(int idUsuario, String nombre, String apellido1, String apellido2, String nombreUsuario, int idRol);

    }

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_login, container, false);

        initView();
        initListeners();
        initObjects();
        initDialog();

        return rootView;
    }

    private void initView() {
        etUserName = rootView.findViewById(R.id.etUserName);
        etUserPassword = rootView.findViewById(R.id.etPasword);
        input_etUserName = rootView.findViewById(R.id.inputUserName);
        input_etUserPassword = rootView.findViewById(R.id.inputPasword);
        tvLogin = rootView.findViewById(R.id.tvLogin);
        btnLogin = rootView.findViewById(R.id.btnLogin);
    }

    private void initObjects() {
        inputValidations = new InputValidations(rootView.getContext());
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyData();
                break;
            case R.id.tvLogin:
                inputValidations.hideKeyboardFrom(tvLogin);
                loginFormActivityListener.performRegister();
                break;
        }
    }

    private void verifyData() {
        //validate is empty
        if (!inputValidations.isInputEditTextFilled(etUserName, input_etUserName, getString(R.string.empty))) {
            etUserName.requestFocus();
            return;
        } else if (!inputValidations.isInputEditTextFilled(etUserPassword, input_etUserPassword, getString(R.string.empty))) {
            etUserPassword.requestFocus();
            return;
            //validate password length
        } else if (!inputValidations.isInputEditTextLength(etUserPassword, input_etUserPassword, getString(R.string.password_length), 8)) {
            etUserPassword.requestFocus();
            return;
        } else {
            //get data
            String userName = etUserName.getText().toString().trim();
            String password = etUserPassword.getText().toString().trim();
            // hide key
            inputValidations.hideKeyboardFrom(btnLogin);
            //submit
            submitData(userName, password);
        }
    }

    private void submitData(String userName, String password) {
        showDialog();

        String passwordEncrypted = "";
        try {
            // encrypt password
            passwordEncrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().usuarioLogin(userName, passwordEncrypted);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            LoginResponse loginResponse = response.body();
                            // Save login status
                            AppPreferences.saveLoginStatus(true, getContext());
                            // Usuario
                            int idUsuario = loginResponse.getData().getId_usuario();
                            String nombre = loginResponse.getData().getName();
                            String apellido1 = loginResponse.getData().getLastName1();
                            String apellido2 = loginResponse.getData().getLastName2();
                            String nombreUsuario = loginResponse.getData().getUser_name();
                            int idRol = loginResponse.getData().getId_rol();
                            loginFormActivityListener.performLogin(idUsuario, nombre, apellido1, apellido2, nombreUsuario, idRol);
                            FancyToast.makeText(getContext(), getString(R.string.code_200), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        } else if (response.code() == 202) {
                            FancyToast.makeText(getContext(), getString(R.string.code_202), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        } else if (response.code() == 422) {
                            FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                        inputValidations.emptyInputEditTexts(etUserPassword);
                    }
                } else {
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("UserLoginFragment", "onFailure: ", t);
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
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }
}