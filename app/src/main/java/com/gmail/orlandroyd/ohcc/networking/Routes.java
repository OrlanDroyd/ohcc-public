package com.gmail.orlandroyd.ohcc.networking;

public interface Routes {
    String BASE_URL = "http://192.168.137.1/slim/public/api/v1/";

    String USER_LOGIN = "usuarios/login";

    String CIUDADANOS_CREAR_USUARIO = "ciudadanos/crear-usuario";
    String CIUDADANO_GET_DATA_USUARIO = "ciudadanos/data-usuario";
    String CIUDADANO_GET_DATA = "ciudadanos/data-join";
    String CIUDADANO_DELETE = "ciudadanos/borrar/{id}";
    String CIUDADANO_UPDATE_BY_ID = "ciudadanos/actualizar/{id}";
    String CIUDADANO_GET_BY_ID = "ciudadano/{id}";

    String ESPECIALISTA_USUARIO_GET = "especialistas/data-usuario";
    String ESPECIALISTA_GET_DATA = "especialistas/data-join";
    String ESPECIALISTA_CREAR_BY_DIRECTIVO = "especialistas/crear-usuario";
    String ESPECIALISTA_GET_BY_ID = "especialista/{id}";
    String ESPECIALISTA_UPDATE_BY_ID = "especialistas/actualizar/{id}";
    String ESPECIALISTA_DELETE = "especialistas/borrar/{id}";

    String DIRECTIVO_USUARIO_GET = "directivos/data-usuario";
    String DIRECTIVO_GET_DATA = "directivos/data-join";
    String DIRECTIVO_GET_BY_ID = "directivo/{id}";
    String DIRECTIVO_CREAR = "directivos/crear-usuario";
    String DIRECTIVO_UPDATE_BY_ID = "directivos/actualizar/{id}";
    String DIRECTIVO_DELETE = "directivos/borrar/{id}";

    String SOLICITUD_BY_ID_USUARIO_AND_TIPO = "solicitudes/usuario-tipo";
    String SOLICITUD_CREAR = "solicitudes/crear";
    String SOLICITUD_UPDATE_BY_USER = "solicitudes/ciudadano-set-descripcion/{id}";
    String SOLICITUD_DELETE = "solicitudes/borrar/{id}";
    String SOLICITUD_GET_BY_ESPECIALISTA = "solicitud/{id}";
    String SOLICITUD_REPORT = "solicitudes/finalizadas/{id}";
    String SOLICITUD_CANCELAR = "solicitudes/cancelar/{id}";
    String SOLICITUD_SET_EVALUACION = "solicitudes/ciudadano-set-evaluacion/{id}";

}
