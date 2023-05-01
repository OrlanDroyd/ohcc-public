package com.gmail.orlandroyd.ohcc.networking.api.services;


import com.gmail.orlandroyd.ohcc.networking.Routes;
import com.gmail.orlandroyd.ohcc.networking.api.response.CiudadanoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.DefaultResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.DirectivoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.EspecialistaResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinCiudadanoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinDirectivoResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.JoinEspecialistaResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.LoginResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudCrearResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudGetAllResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioCiudadanoCrearResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioDirectivoCrearResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioEspecialistaCrearResponse;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by OrlanDroyd on 15/04/2019.
 */
public interface ServiceApi {

    @FormUrlEncoded
    @POST(Routes.USER_LOGIN)
    Call<LoginResponse> usuarioLogin(
            @Field("nombre_usuario") String userName,
            @Field("password") String password
    );

    @GET(Routes.CIUDADANO_GET_DATA_USUARIO)
    Call<UsuarioResponse> getCiudadanoUsuario();

    @GET(Routes.CIUDADANO_GET_DATA)
    Call<JoinCiudadanoResponse> getCiudadanos();

    @FormUrlEncoded
    @POST(Routes.CIUDADANOS_CREAR_USUARIO)
    Call<UsuarioCiudadanoCrearResponse> crearCiudadano(
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("id_rol") int id_rol,
            @Field("ci") String ci,
            @Field("dir_particular") String dir_particular,
            @Field("telef") String telef,
            @Field("email") String email,
            @Field("id_tipo_ciudadano") int id_tipo_ciudadano
    );

    @FormUrlEncoded
    @PUT(Routes.CIUDADANO_UPDATE_BY_ID)
    Call<DefaultResponse> updateCiudadano(
            @Path("id") int id,
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("ci") String ci,
            @Field("dir_particular") String dir_particular,
            @Field("telef") String telef,
            @Field("email") String email,
            @Field("id_tipo_ciudadano") int id_tipo_ciudadano
    );

    @GET(Routes.CIUDADANO_GET_BY_ID)
    Call<CiudadanoResponse> getCiudadanoById(
            @Path("id") int id
    );

    @DELETE(Routes.CIUDADANO_DELETE)
    Call<DefaultResponse> deleteCiudadano(@Path("id") int id);


    @FormUrlEncoded
    @POST(Routes.SOLICITUD_BY_ID_USUARIO_AND_TIPO)
    Call<SolicitudGetAllResponse> getSolicitudByIdAndTipo(
            @Field("id_usuario") int id_usuario,
            @Field("id_tipo_solicitud") int id_tipo_solicitud
    );

    @DELETE(Routes.SOLICITUD_DELETE)
    Call<DefaultResponse> deleteSolicitud(@Path("id") int id);

    @GET(Routes.ESPECIALISTA_USUARIO_GET)
    Call<UsuarioResponse> getEspecialistasUsuario();

    @GET(Routes.ESPECIALISTA_GET_DATA)
    Call<JoinEspecialistaResponse> getEspecialistas();

    @GET(Routes.ESPECIALISTA_GET_BY_ID)
    Call<EspecialistaResponse> getEspecialistaByID(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST(Routes.ESPECIALISTA_CREAR_BY_DIRECTIVO)
    Call<UsuarioEspecialistaCrearResponse> crearEspecialistaByDirectivo(
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("categoria") String categoria
    );

    @FormUrlEncoded
    @PUT(Routes.ESPECIALISTA_UPDATE_BY_ID)
    Call<DefaultResponse> updateEspecialistaByDirectivo(
            @Path("id") int id,
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("categoria") String categoria
    );

    @DELETE(Routes.ESPECIALISTA_DELETE)
    Call<DefaultResponse> deleteEspecialista(@Path("id") int id);

    @GET(Routes.DIRECTIVO_USUARIO_GET)
    Call<UsuarioResponse> getDirectivosUsuario();

    @GET(Routes.DIRECTIVO_GET_DATA)
    Call<JoinDirectivoResponse> getDirectivos();

    @GET(Routes.DIRECTIVO_GET_BY_ID)
    Call<DirectivoResponse> getDirectivoByID(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST(Routes.DIRECTIVO_CREAR)
    Call<UsuarioDirectivoCrearResponse> crearDirectivoByDirectivo(
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("cargo") String cargo
    );

    @FormUrlEncoded
    @PUT(Routes.DIRECTIVO_UPDATE_BY_ID)
    Call<DefaultResponse> updateDirectivoByDirectivo(
            @Path("id") int id,
            @Field("nombre") String name,
            @Field("apellido_1") String lastName1,
            @Field("apellido_2") String lastName2,
            @Field("nombre_usuario") String userName,
            @Field("password") String password,
            @Field("cargo") String cargo
    );

    @DELETE(Routes.DIRECTIVO_DELETE)
    Call<DefaultResponse> deleteDirectivo(@Path("id") int id);

    @FormUrlEncoded
    @POST(Routes.SOLICITUD_CREAR)
    Call<SolicitudCrearResponse> crearSolicitud(
            @Field("fecha_creada") String fecha_creada,
            @Field("descripcion") String descripcion,
            @Field("id_usuario") int id_usuario,
            @Field("id_tipo_solicitud") int id_tipo_solicitud
    );

    @FormUrlEncoded
    @PUT(Routes.SOLICITUD_UPDATE_BY_USER)
    Call<DefaultResponse> updateSolicitudByUsuario(
            @Path("id") int id,
            @Field("fecha_creada") String fecha_creada,
            @Field("descripcion") String descripcion
    );

    @FormUrlEncoded
    @POST(Routes.SOLICITUD_GET_BY_ESPECIALISTA)
    Call<SolicitudGetAllResponse> getSolicitudByEspecialista(
            @Path("id") int id,
            @Field("id_tipo_solicitud") int id_tipo_solicitud,
            @Field("id_especialista") int id_especialista
    );

    @GET(Routes.SOLICITUD_REPORT)
    Call<SolicitudGetAllResponse> getSolicitudesFinalizadas(
            @Path("id") int id
    );

    @PUT(Routes.SOLICITUD_CANCELAR)
    Call<DefaultResponse> cancelarSolicitud(
            @Path("id") int id
    );

    @FormUrlEncoded
    @PUT(Routes.SOLICITUD_SET_EVALUACION)
    Call<DefaultResponse> setEvaluacion(
            @Path("id") int id,
            @Field("evaluacion") int evaluacion
    );
}
