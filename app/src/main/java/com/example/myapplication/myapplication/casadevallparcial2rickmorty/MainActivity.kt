package com.example.myapplication.myapplication.casadevallparcial2rickmorty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var spinnerFiltro: Spinner
    private lateinit var spinnerEstado: Spinner
    private var listaCompleta: List<Personaje> = listOf()

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        spinnerFiltro = findViewById(R.id.spinnerFiltro)
        spinnerEstado = findViewById(R.id.spinnerEstado)

        obtenerPersonajes()
    }

    private fun obtenerPersonajes() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getPersonajes()

        call.enqueue(object : Callback<PersonajesResponse> {
            override fun onResponse(
                call: Call<PersonajesResponse>,
                response: Response<PersonajesResponse>
            ) {
                if (response.isSuccessful) {
                    listaCompleta = response.body()?.results ?: emptyList()

                    val especies = mutableListOf("Todos")
                    especies.addAll(listaCompleta.map { it.species }.distinct())

                    val estados = mutableListOf("Todos")
                    estados.addAll(listaCompleta.map { it.status }.distinct())

                    val adapterEspecie = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, especies)
                    adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerFiltro.adapter = adapterEspecie

                    val adapterEstado = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, estados)
                    adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEstado.adapter = adapterEstado

                    mostrarPersonajes(listaCompleta)

                    val listener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            val seleccionEspecie = spinnerFiltro.selectedItem.toString()
                            val seleccionEstado = spinnerEstado.selectedItem.toString()

                            val filtrados = listaCompleta.filter { personaje: Personaje ->
                                (seleccionEspecie == "Todos" || personaje.species == seleccionEspecie) &&
                                        (seleccionEstado == "Todos" || personaje.status == seleccionEstado)
                            }

                            mostrarPersonajes(filtrados)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }

                    spinnerFiltro.onItemSelectedListener = listener
                    spinnerEstado.onItemSelectedListener = listener
                }
            }

            override fun onFailure(call: Call<PersonajesResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun mostrarPersonajes(lista: List<Personaje>) {
        val adapter = Adapter(this@MainActivity, lista)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("personaje", lista[position])
            startActivity(intent)
        }
    }
}