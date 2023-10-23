package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sabordifapp.model.API.comensal.Comensal
import com.example.sabordifapp.model.API.comensal.ComensalAPI
import com.example.sabordifapp.model.API.comensal.ComensalId
import com.example.sabordifapp.model.API.comensal.ComensalRegistrar
import com.example.sabordifapp.model.API.comensal.Dependencia
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel que se encarga de realizar solicitudes a las API de la app  relacionadas con comensales y dependientes
//Procesa las respuestar y proporciona datos actualizados a la interfaz de usuario
class ComensalVM:ViewModel() {

    //Instancia utilizada para almacenar y observar un valor entero representando el ID del comensal
    val idComensal2 = MutableLiveData<Int>()

    //Configuracion de retrofit para realizar solicitudes HTTP a la API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/comensal/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val descargarAPI by lazy {
        retrofit.create(ComensalAPI::class.java)
    }

    //Funcion que descarga la lista de comensales desde la API de comensales
    fun descargarComensales(callback: (List<Comensal>?) -> Unit){
        val call = descargarAPI.descargarComensales()
        call.enqueue(object : Callback<List<Comensal>> {
            override fun onResponse(call: Call<List<Comensal>>, response: Response<List<Comensal>>){
                if(response.isSuccessful){
                    println("Lista de comensales: ${response.body()}")
                    Log.d("API_TEST", "Lista de comensales: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<Comensal>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }

    //Metodo que descarga la lista de dependientes de un responsable especifico desde la API de comensales
    fun descargarDependientes(idResponsable: Int, callback: (List<Comensal>?) -> Unit){
        val call = descargarAPI.descargarDependientes(idResponsable)
        call.enqueue(object : Callback<List<Comensal>> {
            override fun onResponse(call: Call<List<Comensal>>, response: Response<List<Comensal>>){
                if(response.isSuccessful){
                    println("Lista de dependientes: ${response.body()}")
                    Log.d("API_TEST", "Lista de dependientes: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<Comensal>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }
    private val postearAPI by lazy {
        retrofit.create(ComensalAPI::class.java)
    }

    //Funcion para registrar un nuevo comensal en la API
    fun registrarNuevoComensal(comensal: ComensalRegistrar, callback: (ComensalId?) -> Unit){
        val call = postearAPI.registrarNuevoComensal(comensal)
        call.enqueue(object : Callback<ComensalId> {
            override fun onResponse(call: Call<ComensalId>, response: Response<ComensalId>) {
                if(response.isSuccessful) {
                    println("Comensal creado exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Comensal creado exitosamente: ${response.body()}")
                    idComensal2.value = response.body()?.idComensal
                    callback(response.body())
                }else{
                    callback(null)
                    Log.e("API_TEST", "FAILED")
                }
            }

            override fun onFailure(call: Call<ComensalId>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }

    //Funcion para registrar un nuevo dependiente en la API
    fun registrarDependiente(dependencia: Dependencia, callback: (message?) -> Unit){
        val call = postearAPI.registrarDependiente(dependencia)
        call.enqueue(object : Callback<message> {
            override fun onResponse(call: Call<message>, response: Response<message>) {
                if(response.isSuccessful) {
                    println("Dependencia creado exitosamente: ${response.body()}")
                    callback(response.body())
                    Log.d("API_TEST", "Dependencia creado exitosamente: ${response.body()}")
                }else{
                    callback(null)
                    Log.e("API_TEST", "FAILED")
                }
            }
            //Se maneja cualquier error que pueda ocurrir durante la solicitud
            override fun onFailure(call: Call<message>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }


}