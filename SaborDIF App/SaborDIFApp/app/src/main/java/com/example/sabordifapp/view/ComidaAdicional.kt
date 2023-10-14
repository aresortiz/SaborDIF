package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.viewmodel.ComidaAdicionalViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaAdicionalBinding

class ComidaAdicional : Fragment() {

    //biinding
    private lateinit var binding: FragmentComidaAdicionalBinding
    private val viewModel: ComidaAdicionalViewModel by viewModels()

    companion object {
        fun newInstance() = ComidaAdicional()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaAdicionalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        procederPago()
    }

    private fun procederPago() {
        binding.btnIrPago.setOnClickListener {
            val accion = ComidaAdicionalDirections.actionComidaAdicionalToPagoDonativo()
            findNavController().navigate(accion)
        }
    }


}