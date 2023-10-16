package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.databinding.FragmentDependienteBinding
import com.example.sabordifapp.model.API.comensal.Dependencia
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.ComensalViewModel
import com.example.sabordifapp.viewmodel.DependienteViewModel
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class Dependiente : Fragment() {

    //binding
    private lateinit var binding: FragmentDependienteBinding
    private val viewModel: ComensalVM by viewModels()

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

        escanearQRDependiente()
        escanearQRComensalAsociado()

        binding.btnRegistrarDependiente.setOnClickListener {
            val idDependiente = binding.inputIDDependiente.text.toString().toInt()
            val idComensalAsociado = binding.inputIDComensal.text.toString().toInt()
            Log.d("API_TEST", "Datos dependiente: ${idDependiente} ${idComensalAsociado}")
            val datosDependiente = Dependencia(idDependiente, idComensalAsociado)

            viewModel.registrarDependiente(datosDependiente){ mensaje ->
                if(mensaje != null){
                    val accion = DependienteDirections.actionDependienteToRegistro()
                    findNavController().navigate(accion)
                }
            }
        }
    }

    private fun escanearQRDependiente()
    {
        val qrDependiente = GmsBarcodeScanning.getClient(requireContext())
        binding.escanearQRDependiente.setOnClickListener(){
            qrDependiente.startScan()
                .addOnSuccessListener { idDQR ->
                    val rawValue: String? = idDQR.rawValue
                    val valorIDD = rawValue.toString()
                    val idDependienteQR = valorIDD
                    Log.d("API_TEST", "Este es el raw value ${rawValue}")
                    binding.inputIDDependiente.setText(idDependienteQR)

                }.addOnCanceledListener {
                    //Task cancelled
                }.addOnFailureListener{ e->
                    //Task failed with an exception
                }
        }
    }

    private fun escanearQRComensalAsociado()
    {
        val qrComensalAsociado = GmsBarcodeScanning.getClient(requireContext())
        binding .escanearQRcomensalAsociado.setOnClickListener(){
            qrComensalAsociado.startScan()
                .addOnSuccessListener { idCQR ->
                    val rawValue: String? = idCQR.rawValue
                    val valorIDC = rawValue.toString()
                    val idComensalAsociado = valorIDC
                    Log.d("API_TEST", "Este el es raw value 2 ${rawValue}")
                    binding.inputIDComensal.setText(idComensalAsociado)
                }
        }

    }

}