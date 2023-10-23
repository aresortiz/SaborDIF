package com.example.sabordifapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sabordifapp.model.API.Access.Access
import com.example.sabordifapp.model.API.comedor.Comedor
import com.example.sabordifapp.model.API.comedor.ComedorAPI
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.model.API.responsable.ResponsableAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel que se encarga de realizar solicitudes a las APIs de la aplicacion, procesar las respuestas y
//proporcionar datos actualziados a la interfaz de usuarios a traves de LiveData
class InicioViewModel : ViewModel()
{
    //Instancias que se utilizan para almacenar y observar un objeto Access y listaComedores
    //Estas variables se usan para proporcionar datos actualizados a la interfaz de usuario
    val res = MutableLiveData<Access?>()
    val listaComedores = MutableLiveData<List<Comedor?>>()

    //Configuracion de retrofit para realizar solicitudes HTTP a la API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val postearAPI by lazy {
        retrofit.create(ResponsableAPI::class.java)
    }

    private val getComedores by lazy {
        retrofit.create(ComedorAPI::class.java)
    }

    //Metodo para enviar una solicitud DTTP POST a la API de responsable con los datos de inicio de sesion proporcionados
    fun validarLogin(responsable: Responsable, callback: (Access?) -> Unit){
        val call = postearAPI.validarLogin(responsable)
        call.enqueue(object : Callback<Access> {
            override fun onResponse(call: Call<Access>, response: Response<Access>) {
                if(response.isSuccessful) {
                    res.value = response.body()
                    println("El login es: ${response.body()}")
                    Log.d("API_TEST", "Apertura registrada exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Valor de res: ${res.value}")
                    callback(response.body())
                }else{
                    res.value = response.body()
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }

            //Se maneja cualquier error que pueda ocurrir durante la solicitud
            override fun onFailure(call: Call<Access>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }

    //Descarga la lista de nombres de comedores desde la API de comedores
    fun descargarNombresComedor(callback: (List<Comedor>?) -> Unit){
        val call = getComedores.descargarNombresComedor()
        call.enqueue(object : Callback<List<Comedor>> {
            override fun onResponse(call: Call<List<Comedor>>, response: Response<List<Comedor>>) {
                if(response.isSuccessful){
                    callback(response.body())
                    listaComedores.value = response.body()
                    Log.d("API_TEST", "Lista de comedores: ${listaComedores.value}")
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }
            //Se maneja cualquier error que pueda ocurrir durante la solicitud
            override fun onFailure(call: Call<List<Comedor>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }


}