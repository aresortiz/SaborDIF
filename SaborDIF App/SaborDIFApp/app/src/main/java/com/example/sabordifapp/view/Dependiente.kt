package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
    private val comensalVm: ComensalVM = ComensalVM()
    private var mapaComensal: MutableMap<Int, String> = mutableMapOf()

    companion object {
        fun newInstance() = Dependiente()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDependienteBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtRegisDependiente.startAnimation(slideInAnimatioRight)
        binding.imageView.startAnimation(slideInAnimationLeft)

        binding.txtDependienteDependiente.startAnimation(slideInAnimatioRight)
        binding.inputIDDependiente.startAnimation(slideInAnimatioRight)
        binding.escanearQRDependiente.startAnimation(slideInAnimatioRight)

        binding.imageView2.startAnimation(slideInAnimationLeft)

        binding.txtComensalAsociadoDependiente.startAnimation(slideInAnimatioRight)
        binding.inputIDComensal.startAnimation(slideInAnimatioRight)
        binding.escanearQRcomensalAsociado.startAnimation(slideInAnimatioRight)

        binding.btnRegistrarDependiente.startAnimation(slideInAnimationLeft)
        return binding.root

    }

    private fun escucharModificaciones() {
        val inputDependiente = binding.inputIDDependiente
        inputDependiente.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called during the text change.
            }

            override fun afterTextChanged(s: Editable?) {

                // This method is called after the text is changed.
                val text = s.toString()
                if(s != null && text.isNotEmpty()){
                    val idVal = text.toInt()
                    val nombre = mapaComensal[idVal]
                    var newText = ""
                    newText = if(nombre != null){
                        "Comensal : $nombre"
                    }else{
                        "No hay un Comensal con ese ID"
                    }
                    binding.txtDependienteDependiente.text = newText
                }

                // Perform your operation here with the modified text
            }
        })
        val inputComensalAsociado = binding.inputIDComensal
        inputComensalAsociado.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called during the text change.
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text is changed.
                val text = s.toString()
                if(s != null && text.isNotEmpty()){
                    val idVal = text.toInt()
                    val nombre = mapaComensal[idVal]
                    var newText = ""
                    newText = if(nombre != null){
                        "Comensal Asociado: $nombre"
                    }else{
                        "No hay un Comensal Asociado con ese ID"
                    }
                    binding.txtComensalAsociadoDependiente.text = newText
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        escucharModificaciones()
        registrarEventos()
    }

    private fun registrarEventos() {

        escanearQRDependiente()
        escanearQRComensalAsociado()
        obtenerComensales()

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

    private fun obtenerComensales() {
        comensalVm.descargarComensales { comensales ->
            if(comensales != null){
                for(comensal in comensales){
                    mapaComensal[comensal.IdComensal] = "${comensal.nombres} ${comensal.apellidoPaterno} ${comensal.apellidoMatenro}"
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