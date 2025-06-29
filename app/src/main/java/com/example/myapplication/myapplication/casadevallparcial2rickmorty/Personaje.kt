package com.example.myapplication.myapplication.casadevallparcial2rickmorty


import java.io.Serializable

data class Personaje(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val image: String,
    val location: Location
) : Serializable

data class Location(
    val name: String
) : Serializable

data class PersonajesResponse(
    val results: List<Personaje>
)