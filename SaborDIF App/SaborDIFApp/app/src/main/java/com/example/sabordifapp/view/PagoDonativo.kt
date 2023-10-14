package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.viewmodel.PagoDonativoViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentPagoDonativoBinding

class PagoDonativo : Fragment() {

    //binding
    private lateinit var binding: FragmentPagoDonativoBinding
    private val viewModel:PagoDonativoViewModel by viewModels()

    companion object {
        fun newInstance() = PagoDonativo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagoDonativoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realizarPago()
    }

    private fun realizarPago() {
        binding.btnFinalizar.setOnClickListener {
            val accion = PagoDonativoDirections.actionPagoDonativoToRegistro()
            findNavController().navigate(accion)
        }
    }

}