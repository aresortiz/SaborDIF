package com.example.sabordifapp.view

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentRegistroBinding
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComedorVM
import com.example.sabordifapp.viewmodel.RegistroViewModel

//Fragmento que proporciona opciones para registrar comensales, dependientes y comidas, así como para mostrar información
//sobre comidas vendidas y donadas. Tambien permite a los usuarios contactar al DIF
class RegistroView : Fragment() {

    //binding
    private lateinit var binding: FragmentRegistroBinding
    private val viewModel:RegistroViewModel by viewModels()
    val comedorVm: ComedorVM = ComedorVM()

    companion object {
        fun newInstance() = RegistroView()
    }

    //Animaciones
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistroBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.btnRegistroComensal.startAnimation(slideInAnimationLeft)
        binding.btnRegistroDependiente.startAnimation(slideInAnimationLeft)
        binding.btnRegistroComida.startAnimation(slideInAnimationLeft)

        binding.btnContactarDIF.startAnimation(slideInAnimatioRight)
        binding.btnNotificarCierre.startAnimation(slideInAnimatioRight)
        binding.txtDonadas.startAnimation(slideInAnimatioRight)
        binding.txtVendidas.startAnimation(slideInAnimatioRight)
        binding.contDonadas.startAnimation(slideInAnimatioRight)
        binding.contVendidas.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    //Llamada a las funciones necesarias
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComensal()
        registrarDependiente()
        registrarComida()
        descargarRegistroDeComidasVendidas()
        contactarDIF()

    }

    //Metodo que obtiene el ID del comedot alamacenado en las preferencias compartidas
    //Se utiliza ese ID para descargar  informacion sobre comidas vendidas y donadas, actualizando las vistas con los valores obtenidos
    private fun descargarRegistroDeComidasVendidas() {
        val prefs: SharedPreferences = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
        val defaultValue = "0"
        val idComedor = prefs.getString("IDComedor", defaultValue)?.toInt()
        if(idComedor != null){
            comedorVm.descargarVendidasDonadas(idComedor){comidasVendidas ->
                if(comidasVendidas != null){
                    binding.contVendidas.setText(comidasVendidas.totalComidasVendidas.toString())
                    binding.contDonadas.setText(comidasVendidas.totalComidasDonadas.toString())
                }
            }
        }
    }

    //Metodo que se llama cuando se da clic en el boton registro comensal, lo cual lleva al fragmento Comensal
    private fun registrarComensal(){
        binding.btnRegistroComensal.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistroComensal.toString()

            //Log.e("Prueba", "boton para pasar a comensal")
            val accion = RegistroViewDirections.actionRegistroToComensal()
            findNavController().navigate(accion)
        }
    }

    //Metodo que se llama cuando se da clic en el boton registro dependietne, navegando asi al fragmento Dependiente
    private fun registrarDependiente(){
        binding.btnRegistroDependiente.setOnClickListener {
            val accion = RegistroViewDirections.actionRegistroToDependiente()
            findNavController().navigate(accion)
        }
    }

    //Metodo que se llama al hacer clic en el boton registro comida, lo que llevara a la navegacion del fragmento Dependiente
    private fun registrarComida(){
        binding.btnRegistroComida.setOnClickListener {
            val accion = RegistroViewDirections.actionRegistroToOpcionesComida()
            findNavController().navigate(accion)
        }
    }

    //Metodo que se llama al hacer clic en el boton contactar DIF
    //Se iniciara una intencion para marcar un numero telefonico utilizando la app de llamadas del dispositivo
    private fun contactarDIF(){
        binding .btnContactarDIF.setOnClickListener{
            Log.d("API_TEST", "Boton presionado")
            val numeroTelefonico = "0000000000"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$numeroTelefonico")

            if(intent.resolveActivity(requireContext().packageManager) != null){
                startActivity(intent)
            }else{
                //Handle exception
            }
        }
    }


}