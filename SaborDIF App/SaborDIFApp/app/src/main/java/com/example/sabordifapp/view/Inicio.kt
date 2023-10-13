package com.example.sabordifapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentInicioBinding
import com.example.sabordifapp.viewmodel.InicioViewModel

class Inicio : Fragment() {

    //View binding
    private lateinit var binding: FragmentInicioBinding
    private val viewModel: InicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        //registrarObservables()
    }

    private fun registrarEventos(){
        binding.btnIngresar.setOnClickListener {
            val tipo = binding.idComedor.selectedItem.toString()
            Log.d("Prueba", "boton para pasar a registro ${tipo}")

            val accion = InicioDirections.actionInicioToRegistro()
            findNavController().navigate(accion)


        }
    }

}
