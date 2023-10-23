package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sabordifapp.viewmodel.PagoDonativoViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentPagoDonativoBinding

//Fragmento que muestra el monto total a pagar y permite al usuario finalizar el proceso de pago
class PagoDonativo : Fragment() {

    //binding
    private lateinit var binding: FragmentPagoDonativoBinding
    private val viewModel:PagoDonativoViewModel by viewModels()
    var totalPorPagar = 0


    companion object {
        fun newInstance() = PagoDonativo()
    }

    //Animaciones
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagoDonativoBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtPagoDonativo.startAnimation(slideInAnimationLeft)
        binding.imagPagoDonativo.startAnimation(slideInAnimatioRight)
        binding.textView2.startAnimation(slideInAnimationLeft)
        binding.btnFinalizar.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    //Se obtienen los argumentos pasados y se almacena el valor total a pagar, luego se actualiza el texto con el monto toal
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.e("PAGO DONATIVO", "El pago total es de $totalPorPagar")
        val args: PagoDonativoArgs by navArgs()
        totalPorPagar = args.totalPorPagar
        binding.textView2.text = "Total a pagar: $$totalPorPagar"
        realizarPago()
    }

    //Metodo que se llama cuando se hace clic en el boton finalizar, navegando al fragmento Registro
    private fun realizarPago() {
        binding.btnFinalizar.setOnClickListener {
            val accion = PagoDonativoDirections.actionPagoDonativoToRegistro()
            findNavController().navigate(accion)
        }
    }

}