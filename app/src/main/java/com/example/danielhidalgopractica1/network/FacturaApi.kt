package com.example.practica1danielhidalgo.network
import com.example.danielhidalgopractica1.model.RespuestaFactura
import retrofit2.Response
import retrofit2.http.GET

interface FacturaApi {
    @GET("/facturas")
    suspend fun getFacturas(): Response<RespuestaFactura>
}