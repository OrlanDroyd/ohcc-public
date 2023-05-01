package com.gmail.orlandroyd.ohcc.networking.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OrlanDroyd on 16/05/2019.
 */
public class Solicitud {

    @SerializedName("id_solicitud")
    private int id_solicitud;

    @SerializedName("evaluacion")
    private int evaluacion;

    @SerializedName("respuesta")
    private String respuesta;

    @SerializedName("fecha_creada")
    private String fecha_creada;

    @SerializedName("fecha_iniciada")
    private String fecha_iniciada;

    @SerializedName("fecha_respuesta")
    private String fecha_respuesta;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("id_usuario")
    private int id_usuario;

    @SerializedName("id_especialista")
    private int id_especialista;

    @SerializedName("id_tipo_solicitud")
    private int id_tipo_solicitud;

    @SerializedName("id_estado")
    private int id_estado;

    public Solicitud(int id_solicitud, int evaluacion, String respuesta, String fecha_creada, String fecha_iniciada, String fecha_respuesta, String descripcion, int id_usuario, int id_especialista, int id_tipo_solicitud, int id_estado) {
        this.id_solicitud = id_solicitud;
        this.evaluacion = evaluacion;
        this.respuesta = respuesta;
        this.fecha_creada = fecha_creada;
        this.fecha_iniciada = fecha_iniciada;
        this.fecha_respuesta = fecha_respuesta;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
        this.id_especialista = id_especialista;
        this.id_tipo_solicitud = id_tipo_solicitud;
        this.id_estado = id_estado;
    }

    public Solicitud() {
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getFecha_creada() {
        return fecha_creada;
    }

    public String getFecha_iniciada() {
        return fecha_iniciada;
    }

    public String getFecha_respuesta() {
        return fecha_respuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId_especialista() {
        return id_especialista;
    }

    public int getId_tipo_solicitud() {
        return id_tipo_solicitud;
    }

    public int getId_estado() {
        return id_estado;
    }
}
