package com.example.myapplication.myapplication.casadevallparcial2rickmorty

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    fun getPersonajes(): Call<PersonajesResponse>
}