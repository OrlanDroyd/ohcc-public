package com.gmail.orlandroyd.ohcc.map;

/**
 * Created by OrlanDroyd on 25/02/2019.
 */
public class Place {
    private String nombre;
    private String descripcion;
    private double latitud;
    private double longitud;

    public Place(String nombre, String descripcion, double latitud, double longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
