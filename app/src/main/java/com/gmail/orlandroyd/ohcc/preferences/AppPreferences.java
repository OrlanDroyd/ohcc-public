package com.gmail.orlandroyd.ohcc.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gmail.orlandroyd.ohcc.util.Constants;

/**
 * Created by OrlanDroyd on 30/04/19
 */
public class AppPreferences {

    private static final String nombre = "Registrarse...";
    private static final String nombreUsuario = "Click aquí";

    public AppPreferences() {
    }

    /**
     * Usado en WelcomeActivity
     *
     * @param context
     * @param isFirstTime
     */
    public static void setFirstTimeLaunch(Context context, boolean isFirstTime) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_WELCOME_STATUS, isFirstTime);
        editor.commit();
    }

    /**
     * Usado en WelcomeActivity
     *
     * @param context
     * @return
     */
    public static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_WELCOME_STATUS, true);
    }

    /**
     * Para saber si esta logeado save
     *
     * @param status
     * @param context
     */
    public static void saveLoginStatus(boolean status, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_LOGIN_STATUS, status);
        editor.commit();
    }

    /**
     * Para saber si esta logeado get
     *
     * @param context
     * @return
     */
    public static boolean getLoginStatus(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_LOGIN_STATUS, false);
    }

    /**
     * saveFragmentTramite
     *
     * @param i
     * @param context
     * @return
     */
    public static boolean saveFragmetPos(int i, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.FRAGMENT_POS, i);
        editor.commit();
        return true;
    }

    /**
     * getFragmentTramite
     *
     * @param context
     * @return
     */
    public static int getFragmetPos(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.FRAGMENT_POS, 1);
    }

    /**
     * saveIdUser
     *
     * @param i
     * @param context
     * @return
     */
    public static boolean saveIdUsuario(int i, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.KEY_ID_USUARIO, i);
        editor.commit();
        return true;
    }

    /**
     * getIdUser
     *
     * @param context
     * @return
     */
    public static int getIdUsuario(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.KEY_ID_USUARIO, -1);
    }

    /**
     * saveNombre
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveNombre(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_NOMBRE, s);
        editor.commit();
        return true;
    }

    /**
     * getNombre
     *
     * @param context
     * @return
     */
    public static String getNombre(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_NOMBRE, nombre);
    }

    /**
     * saveApellido1
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveApellido1(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_APELLIDO1, s);
        editor.commit();
        return true;
    }

    /**
     * getApellido1
     *
     * @param context
     * @return
     */
    public static String getApellido1(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_APELLIDO1, "");
    }

    /**
     * saveApellido2
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveApellido2(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_APELLIDO2, s);
        editor.commit();
        return true;
    }

    /**
     * getApellido2
     *
     * @param context
     * @return
     */
    public static String getApellido2(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_APELLIDO2, "");
    }

    /**
     * saveNombreUsuario
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveNombreUsuario(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_NOMBRE_USUARIO, s);
        editor.commit();
        return true;
    }

    /**
     * getNombreUsuario
     *
     * @param context
     * @return
     */
    public static String getNombreUsuario(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_NOMBRE_USUARIO, nombreUsuario);
    }

    /**
     * saveRol
     *
     * @param i
     * @param context
     * @return
     */
    public static boolean saveRol(int i, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.KEY_ID_ROL, i);
        editor.commit();
        return true;
    }

    /**
     * getRol
     *
     * @param context
     * @return
     */
    public static int getRol(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.KEY_ID_ROL, -1);
    }

    /**
     * saveCi
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveCi(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_CI, s);
        editor.commit();
        return true;
    }

    /**
     * getCi
     *
     * @param context
     * @return
     */
    public static String getCi(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_CI, "");
    }

    /**
     * saveDirPart
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveDirPart(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_DIR_PARTICULAR, s);
        editor.commit();
        return true;
    }

    /**
     * getDiPart
     *
     * @param context
     * @return
     */
    public static String getDirPart(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_DIR_PARTICULAR, "");
    }

    /**
     * saveTelef
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveTelef(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_TELEF, s);
        editor.commit();
        return true;
    }

    /**
     * getTelef
     *
     * @param context
     * @return
     */
    public static String getTelef(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_TELEF, "");
    }

    /**
     * saveEmail
     *
     * @param s
     * @param context
     * @return
     */
    public static boolean saveEmail(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_EMAIL, s);
        editor.commit();
        return true;
    }

    /**
     * getEmail
     *
     * @param context
     * @return
     */
    public static String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_EMAIL, "");
    }

    /**
     * saveTipoCidadano
     *
     * @param i
     * @param context
     * @return
     */

    public static boolean saveTipoCid(int i, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.KEY_ID_TIPO_CIUADANO, i);
        editor.commit();
        return true;
    }

    /**
     * getTipoCidadano
     *
     * @param context
     * @return
     */
    public static int getTipoCiu(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.KEY_ID_TIPO_CIUADANO, -1);
    }


    public static boolean saveCategoria(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_CATEGORIA, s);
        editor.commit();
        return true;
    }


    public static String getCategoria(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_CATEGORIA, "");
    }

    public static boolean saveCargo(String s, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_CARGO, s);
        editor.commit();
        return true;
    }


    public static String getCargo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_CARGO, "");
    }
}
