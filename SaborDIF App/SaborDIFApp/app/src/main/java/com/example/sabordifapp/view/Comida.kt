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
import com.example.sabordifapp.databinding.FragmentComidaBinding
import com.example.sabordifapp.viewmodel.ComidaViewModel

class Comida : Fragment() {

    //binding
    private lateinit var binding: FragmentComidaBinding
    private val viewModel:ComidaViewModel by viewModels()

    companion object {
        fun newInstance() = ComensalView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComidaAdicional()
        registrarComida()
    }

    private fun registrarComidaAdicional() {
        binding.btnSi.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistrar.isSelected.toString()
            val accion = ComidaDirections.actionComidaToComidaAdicional()
            findNavController().navigate(accion)
        }
    }

    private fun registrarComida(){
        binding.btnNo.setOnClickListener {
            val accion = ComidaDirections.actionComidaToPagoDonativo()
            findNavController().navigate(accion)
        }
    }


}