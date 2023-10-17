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

class RegistroView : Fragment() {

    //binding
    private lateinit var binding: FragmentRegistroBinding
    private val viewModel:RegistroViewModel by viewModels()
    val comedorVm: ComedorVM = ComedorVM()

    companion object {
        fun newInstance() = RegistroView()
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComensal()
        registrarDependiente()
        registrarComida()
        descargarRegistroDeComidasVendidas()
        contactarDIF()

    }

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

    private fun registrarComensal(){
        binding.btnRegistroComensal.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistroComensal.toString()

            //Log.e("Prueba", "boton para pasar a comensal")
            val accion = RegistroViewDirections.actionRegistroToComensal()
            findNavController().navigate(accion)
        }
    }

    private fun registrarDependiente(){
        binding.btnRegistroDependiente.setOnClickListener {
            val accion = RegistroViewDirections.actionRegistroToDependiente()
            findNavController().navigate(accion)
        }
    }

    private fun registrarComida(){
        binding.btnRegistroComida.setOnClickListener {
            val accion = RegistroViewDirections.actionRegistroToOpcionesComida()
            findNavController().navigate(accion)
        }
    }

    private fun contactarDIF(){
        binding .btnContactarDIF.setOnClickListener{
            Log.d("API_TEST", "Boton presionado")
            val numeroTelefonico = "5528178518"
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