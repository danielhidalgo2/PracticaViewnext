package com.example.danielhidalgopractica1.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WrapperFiltro {

    public int importeMaximo=0;
    public int importeFiltro=0;
    public List<String> estadosFiltro= new ArrayList <>() ;
    public String fecha_desde="" ;
    public String fecha_hasta="" ;

    public WrapperFiltro(){}

    public void setEstadosFiltro(List<String> estadosFiltro) {

        this.estadosFiltro = estadosFiltro;
    }

    public int getImporteMaximo() {

        return importeMaximo;
    }

    public void setImporteMaximo(int importeMaximo) {

        this.importeMaximo = importeMaximo;
    }

    public int getImporteFiltro() {

        return importeFiltro;
    }

    public void setImporteFiltro(int importeFiltro) {

        this.importeFiltro = importeFiltro;
    }

    public List<String> getEstadosFiltro() {

        return estadosFiltro;
    }

    @NonNull
    @Override
    public String toString() {
        return "WrapperFiltro{" +
                "importeMaximo=" + importeMaximo +
                ", importeFiltro=" + importeFiltro +
                ", estadosFiltro=" + estadosFiltro +
                ", fecha_desde=" + fecha_desde +
                ", fecha_hasta=" + fecha_hasta +
                '}';
    }

    public void introducirEstadoFiltro(String estado) {

        this.estadosFiltro.add(estado);
    }

    public void eliminarEstadoFiltro(String estado){

        if(this.estadosFiltro.contains(estado)){
            this.estadosFiltro.remove(estado);
        }
    }

    public String getFecha_desde() {

        return fecha_desde;
    }

    public void setFecha_desde(String fecha_desde) {

        this.fecha_desde = fecha_desde;
    }

    public String getFecha_hasta() {

        return fecha_hasta;
    }

    public void setFecha_hasta(String fecha_hasta) {

        this.fecha_hasta = fecha_hasta;
    }
}
