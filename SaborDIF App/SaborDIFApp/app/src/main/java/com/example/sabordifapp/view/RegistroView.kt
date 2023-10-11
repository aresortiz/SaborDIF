package com.example.sabordifapp.view

import android.os.Bundle
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
        registrarEventos()
    }

    private fun registrarEventos(){
        binding.btnRegistroComensal.setOnClickListener {
            val accion = RegistroViewDirections.actionRegistroToComensal()
            findNavController().navigate(accion)
        }
    }


}