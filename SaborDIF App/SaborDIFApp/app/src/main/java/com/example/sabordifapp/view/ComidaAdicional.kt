package com.example.sabordifapp.view

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.viewmodel.ComidaAdicionalViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaAdicionalBinding
import com.example.sabordifapp.model.API.comida.Comida
import com.example.sabordifapp.model.API.comida.ComidaDependiente
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComidaVM
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlin.math.cos

class ComidaAdicional : Fragment() {

    //biinding
    var listaComidas: MutableList<ComidaDependiente> = mutableListOf()
    var costeFinal = 0
    private lateinit var binding: FragmentComidaAdicionalBinding
    private val viewModel: ComidaAdicionalViewModel by viewModels()
    private var comidaVm: ComidaVM = ComidaVM()

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
        registrarEventos()
    }

    private fun registrarEventos() {
        escanearQRDependiente()
        escanearQRComensalAsociado()
        agregarComida()
        enviarComida()
    }

    private fun enviarComida() {
        binding.btnIrPago.setOnClickListener{
            Log.d("COMIDA ADICIONAL", "$listaComidas")
            comidaVm.registrarComidaDependiente(listaComidas)
            val accion = ComidaAdicionalDirections.actionComidaAdicionalToPagoDonativo(costeFinal)
            findNavController().navigate(accion)
        }

    }

    private fun agregarComida() {
        binding.btnAgregarAsociadoComida.setOnClickListener {
            val prefs: SharedPreferences = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
            val defaultValue = "0"
            val idComedor = prefs.getString("IDComedor", defaultValue)?.toInt()

            val idDependiente = binding.inputDependienteComida.text.toString().toInt()
            val idComensalAsociado = binding.inputComensalAsociadoComida.text.toString().toInt()
            val valorPago = binding.spinnerPagoComidaDependiente.selectedItem.toString()
            val valorSitio = binding.spinnerLlevarComidaDependiente.selectedItem.toString()
            var aportacion =  0
//            var idComedor = 0
            if(valorPago[0] == 'P'){
                aportacion= 13
            }
            var paraLlevar = 0
            if(valorSitio[0] == 'P'){
                paraLlevar = 1
            }
            costeFinal+=aportacion
            if(idComedor != null){
                listaComidas.add(ComidaDependiente(idComedor, idDependiente, idComensalAsociado, aportacion, paraLlevar))
                binding.inputComensalAsociadoComida.setText("")
            }
        }

    }

    private fun escanearQRDependiente() {
        val qrDependiente = GmsBarcodeScanning.getClient(requireContext())
        binding.escanearQRDependienteRegistroComida.setOnClickListener{
            qrDependiente.startScan()
                .addOnSuccessListener { idDQR ->
                    val rawValue: String? = idDQR.rawValue
                    val valorIDD = rawValue.toString()
                    val idDependienteQR = valorIDD
                    Log.d("API_TEST", "Este es el raw value ${rawValue}")
                    binding.inputDependienteComida.setText(idDependienteQR)
                }.addOnCanceledListener {

                }.addOnFailureListener{e ->

                }
        }
    }

    private fun escanearQRComensalAsociado() {
        val qrComensalAsociado = GmsBarcodeScanning.getClient(requireContext())
        binding.escanearQRComensalAsociadoComida.setOnClickListener{
            qrComensalAsociado.startScan()
                .addOnSuccessListener { idDQR ->
                    val rawValue: String? = idDQR.rawValue
                    val valorIDD = rawValue.toString()
                    val IdcomensalAsociado= valorIDD
                    Log.d("API_TEST", "Este es el raw value ${rawValue}")
                    binding.inputComensalAsociadoComida.setText(IdcomensalAsociado)
                }.addOnCanceledListener {

                }.addOnFailureListener{e ->

                }
        }
    }
}