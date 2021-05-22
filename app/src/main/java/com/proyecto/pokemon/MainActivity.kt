package com.proyecto.pokemon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){

    private lateinit var retrofit: Retrofit 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        obtenerDatos()
    }

    private fun obtenerDatos() {
        val service:PokeApi = retrofit.create(PokeApi::class.java)
       val pokemonrtaCall :Call<Pokemonrta> = service.obtenerPokemon()

        pokemonrtaCall.enqueue(object: Callback <Pokemonrta> {
            override fun onFailure(call: Call<Pokemonrta>?, t: Throwable?) {
                Log.d("No", "Consulta Fallida")
            }

            override fun onResponse(call: Call<Pokemonrta>?, response: Response<Pokemonrta>?) {
                if (response!!.isSuccessful) {
                    val pokemonrta:Pokemonrta = response.body()
                    val lista:ArrayList<Pokemon> = pokemonrta.results

                    for (i in 0..lista.size){
                        val p:Pokemon = lista.get(i)
                        Log.d("Pokemon","Pokemon: " + p.name)
                    }

                }else{
                    Log.d("No", "Conexión Fallida")
                }
            }
        })

    }
}






