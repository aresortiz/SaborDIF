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

class PagoDonativo : Fragment() {

    //binding
    private lateinit var binding: FragmentPagoDonativoBinding
    private val viewModel:PagoDonativoViewModel by viewModels()
    var totalPorPagar = 0


    companion object {
        fun newInstance() = PagoDonativo()
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.e("PAGO DONATIVO", "El pago total es de $totalPorPagar")
        val args: PagoDonativoArgs by navArgs()
        totalPorPagar = args.totalPorPagar
        binding.textView2.text = "Total a pagar: $$totalPorPagar"
        realizarPago()
    }

    private fun realizarPago() {
        binding.btnFinalizar.setOnClickListener {
            val accion = PagoDonativoDirections.actionPagoDonativoToRegistro()
            findNavController().navigate(accion)
        }
    }

}