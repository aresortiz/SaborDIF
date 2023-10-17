package com.example.sabordifapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentRegistroBinding
import com.example.sabordifapp.viewmodel.RegistroViewModel

class RegistroView : Fragment() {

    //binding
    private lateinit var binding: FragmentRegistroBinding
    private val viewModel:RegistroViewModel by viewModels()

    companion object {
        fun newInstance() = RegistroView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComensal()
        registrarDependiente()
        registrarComida()
        contactarDIF()
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