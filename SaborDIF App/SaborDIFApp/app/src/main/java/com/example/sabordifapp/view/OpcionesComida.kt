package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.viewmodel.OpcionesComidaViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaAdicionalBinding
import com.example.sabordifapp.databinding.FragmentOpcionesComidaBinding
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComidaVM

class OpcionesComida : Fragment() {

    private lateinit var binding: FragmentOpcionesComidaBinding
    companion object {
        fun newInstance() = OpcionesComida()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpcionesComidaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComidaComensal()
        registrarComidaDependiente()
    }

    private fun registrarComidaDependiente(){
        binding.btnComidaDependiente.setOnClickListener {
            val accion = OpcionesComidaDirections.actionOpcionesComidaToComidaAdicional()
            findNavController().navigate(accion)
        }
    }
    private fun registrarComidaComensal(){
        binding.btnComidaComensal.setOnClickListener {
            val accion = OpcionesComidaDirections.actionOpcionesComidaToComida()
            findNavController().navigate(accion)
        }
    }

}