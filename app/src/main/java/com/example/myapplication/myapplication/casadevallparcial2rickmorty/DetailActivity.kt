package com.example.myapplication.myapplication.casadevallparcial2rickmorty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val personaje = intent.getSerializableExtra("personaje") as? Personaje

        val nombreTextView = findViewById<TextView>(R.id.nombreTextView)
        val especieTextView = findViewById<TextView>(R.id.especieTextView)
        val locationTextView = findViewById<TextView>(R.id.locationTextView)
        val imagenImageView = findViewById<ImageView>(R.id.imagenImageView)

        personaje?.let {
            nombreTextView.text = it.name
            especieTextView.text = "Especie: ${it.species}"
            locationTextView.text = "Ubicación: ${it.location.name}"

            Picasso.get()
                .load(it.image)
                .into(imagenImageView)
        }
    }
}