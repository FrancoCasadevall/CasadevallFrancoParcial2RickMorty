package com.example.myapplication.myapplication.casadevallparcial2rickmorty

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso

class Adapter(private val context: Context, private val dataSource: List<Personaje>) :
    ArrayAdapter<Personaje>(context, 0, dataSource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val personaje = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val nombreTextView = view.findViewById<TextView>(R.id.nombreTextView)
        val imagenImageView = view.findViewById<ImageView>(R.id.imagenImageView)

        nombreTextView.text = personaje?.name

        // Para poder ver las imagenes de la API, use libreria picasso
        Picasso.get()
            .load(personaje?.image)
            .into(imagenImageView)

        return view
    }
}