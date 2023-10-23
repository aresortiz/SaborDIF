package com.example.sabordifapp.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentBienvenidaBinding
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComedorVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ResponsableVM
import com.example.sabordifapp.viewmodel.BienvenidaViewModel

//Fragmento que se utiliza para mostrar una pantalla de bienvenidad en la aplicacion
class BienvenidaView : Fragment() {

    //var responsableC: ResponsableVM = ResponsableVM()
    //var comedorKK: ComedorVM = ComedorVM()

    //binding
    private lateinit var binding: FragmentBienvenidaBinding
    private val viewModel:BienvenidaViewModel by viewModels()

    companion object {
        fun newInstance() = BienvenidaView()
    }

    //Se infla la vista y se devuelve la raíz de la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBienvenidaBinding.inflate(layoutInflater)
        return binding.root
    }

    //Se configura la animacion para varios elementos de la interfaz del usuario
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        val duration: Long = 900
        val logoApp = view.findViewById<ImageView>(R.id.logoApp)
        moveImage(logoApp, 200f, 200f, duration)
        val botonIniciar = view.findViewById<AppCompatButton>(R.id.btnIniciarSesion)
        moveImage(botonIniciar, 200f, 200f, duration)
        val txtBienvenida = view.findViewById<TextView>(R.id.txtBienvenida)
        moveImage(txtBienvenida, 200f, 200f, duration)



    }

    //Metodo utilizado para animar la escala (cambio de tamaño) de un elemento de la interfaz de usuario
    private fun moveImage(view: View, translationX: Float, translationY: Float, duration: Long) {

            // Escala desde 0.1x (pequeña) a 1.0x (tamaño normal)
            val scaleXAnim = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.1f, 1.0f)
            val scaleYAnim = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.1f, 1.0f)

            // Combina las animaciones en un conjunto
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleXAnim, scaleYAnim)
            animatorSet.duration = duration

            // Inicia la animación
            animatorSet.start()
    }

//Se registra un evento de clic para el boton de inicio de sesion
private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener {
            val accion = BienvenidaViewDirections.actionBienvenidaToInicio()
            findNavController().navigate(accion)
        }
    }

}