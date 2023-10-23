package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.viewmodel.OpcionesComidaViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaAdicionalBinding
import com.example.sabordifapp.databinding.FragmentOpcionesComidaBinding
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComidaVM

//Clase que proprociona una pantalla con dos opciones para el usuario: registrar comida para un dependiente o para un comensal
class OpcionesComida : Fragment() {

    private lateinit var binding: FragmentOpcionesComidaBinding
    companion object {
        fun newInstance() = OpcionesComida()
    }

    //Animaciones
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpcionesComidaBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtRegisComida.startAnimation(slideInAnimationLeft)
        binding.imagComida.startAnimation(slideInAnimatioRight)
        binding.btnComidaComensal.startAnimation(slideInAnimationLeft)
        binding.btnComidaDependiente.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    //Llamada a funciones
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarComidaComensal()
        registrarComidaDependiente()
    }

    //Metodo que se llama al hacer clic en el boton comida dependiente, y se navega al fragmento ComidaAdicional
    private fun registrarComidaDependiente(){
        binding.btnComidaDependiente.setOnClickListener {
            val accion = OpcionesComidaDirections.actionOpcionesComidaToComidaAdicional()
            findNavController().navigate(accion)
        }
    }

    //Metodo que se llama al hacer clic en el boton comida comensal, y se navega al fragmento ComidaComensal
    private fun registrarComidaComensal(){
        binding.btnComidaComensal.setOnClickListener {
            val accion = OpcionesComidaDirections.actionOpcionesComidaToComida()
            findNavController().navigate(accion)
        }
    }

}