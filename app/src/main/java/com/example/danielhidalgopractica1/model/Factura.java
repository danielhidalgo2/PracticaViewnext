package com.example.danielhidalgopractica1.model;

import androidx.annotation.Nullable;

public class Factura {

    public String descEstado;
    public double importeOrdenacion;
    public String fecha;

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
    public Factura(String descEstado, double importe, String fecha) {
        this.descEstado = descEstado;
        this.importeOrdenacion = importe;
        this.fecha = fecha;
    }
}
