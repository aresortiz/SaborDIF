package com.example.sabordifapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.viewmodel.ComensalViewModel

class ComensalView : Fragment() {

    //binding
    private lateinit var binding: FragmentComensalBinding
    private val viewModel:ComensalViewModel by viewModels()

    companion object {
        fun newInstance() = ComensalView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComensalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnRegistrar.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistrar.isSelected.toString()
            val accion = ComensalViewDirections.actionComensalToRegistro()
            findNavController().navigate(accion)
        }
    }

}