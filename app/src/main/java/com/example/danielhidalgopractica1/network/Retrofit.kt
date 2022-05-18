package com.example.practica1danielhidalgo

import com.example.danielhidalgopractica1.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()
}