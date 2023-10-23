package com.example.sabordifapp.viewmodel.APIVM.viewmodel
import android.util.Log
import com.example.sabordifapp.model.API.comedor.Comedor
import com.example.sabordifapp.model.API.comedor.ComedorAPI
import com.example.sabordifapp.model.API.comedor.ComidasVendidas
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel que se utilzia para realizar oepraciones relacionadas con los comedores
class ComedorVM {

    //Configuracion de retrofit para realizar solicitudes HTTP a la API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/comedor/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val descargarAPI by lazy {
        retrofit.create(ComedorAPI::class.java)
    }

    //Metodo que descarga la lista de nombres de comedores desde el servidor
    //Toma una funcion de devolucion de llamada (callback) como parametro para manejar los resultados
    fun descargarNombresComedor(callback: (List<Comedor>?) -> Unit){
        val call = descargarAPI.descargarNombresComedor()
        call.enqueue(object : Callback<List<Comedor>> {
            override fun onResponse(call: Call<List<Comedor>>, response: Response<List<Comedor>>){
                if(response.isSuccessful){
                    println("Lista de comedores: ${response.body()}")
                    Log.d("API_TEST", "Lista de comedores: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<Comedor>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }

    //Se configura otro objeto retro para interacturar con la API relacionada con las comidas vendidas
    private val retro by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/graficas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val descargarVendidasAPI by lazy {
        retro.create(ComedorAPI::class.java)
    }

    //Metodo que se utiliza para descargar la cantidad de comidas vendidas y donadas para un comedor especifico
    fun descargarVendidasDonadas(idComedor: Int, callback: (ComidasVendidas?) -> Unit){
        val call = descargarVendidasAPI.descargarVendidasDonadas(idComedor)
        call.enqueue(object : Callback<ComidasVendidas> {
            override fun onResponse(call: Call<ComidasVendidas>, response: Response<ComidasVendidas>) {
                if(response.isSuccessful) {
                    println("Dependencia creado exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Vendidas y Dondadas recuperadas: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }

            //Se maneja cualquier error que pueda ocurrir durante la solicitud
            override fun onFailure(call: Call<ComidasVendidas>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }
}