package com.example.danielhidalgopractica1.model;

import java.util.List;

public class RespuestaFactura {
    public List<Factura> facturas;
    public String numFacturas;

    public RespuestaFactura(List<Factura> facturas, String numFacturas){
        this.facturas=facturas;
        this.numFacturas=numFacturas;
    }
}
