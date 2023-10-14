package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentBienvenidaBinding
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComedorVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ResponsableVM
import com.example.sabordifapp.viewmodel.BienvenidaViewModel

class BienvenidaView : Fragment() {

    //var responsableC: ResponsableVM = ResponsableVM()
    //var comedorKK: ComedorVM = ComedorVM()

    //bidning
    private lateinit var binding: FragmentBienvenidaBinding
    private val viewModel:BienvenidaViewModel by viewModels()

    companion object {
        fun newInstance() = BienvenidaView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBienvenidaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //responsableC.validarLogin(Responsable(1, "cisco123"))
        //comedorKK.descargarNombresComedor()
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener {
            val accion = BienvenidaViewDirections.actionBienvenidaToInicio()
            findNavController().navigate(accion)
        }
    }

}