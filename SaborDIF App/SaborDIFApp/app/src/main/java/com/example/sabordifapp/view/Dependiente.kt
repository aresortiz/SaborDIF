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
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.databinding.FragmentDependienteBinding
import com.example.sabordifapp.viewmodel.ComensalViewModel
import com.example.sabordifapp.viewmodel.DependienteViewModel

class Dependiente : Fragment() {

    //binding
    private lateinit var binding: FragmentDependienteBinding
    private val viewModel: DependienteViewModel by viewModels()

    companion object {
        fun newInstance() = Dependiente()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDependienteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnRegistrarDependiente.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistrar.isSelected.toString()
            val accion = DependienteDirections.actionDependienteToRegistro()
            findNavController().navigate(accion)
        }
    }

}