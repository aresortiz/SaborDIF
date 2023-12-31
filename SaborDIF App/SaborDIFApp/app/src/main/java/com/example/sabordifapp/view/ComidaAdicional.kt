package com.example.sabordifapp.view

import android.content.Context
import android.content.SharedPreferences
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
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sabordifapp.viewmodel.ComidaAdicionalViewModel
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaAdicionalBinding
import com.example.sabordifapp.model.API.comida.Comida
import com.example.sabordifapp.model.API.comida.ComidaDependiente
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComidaVM
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlin.math.cos

//Clase para hacer el registro de una comida adicional
class ComidaAdicional : Fragment() {

    //binding
    var listaComidas: MutableList<ComidaDependiente> = mutableListOf()
    var mapaComensalAsociado: MutableMap<String, Int> = mutableMapOf()
    var mapaComensal: MutableMap<Int, String> = mutableMapOf()
    var costeFinal = 0
    private lateinit var binding: FragmentComidaAdicionalBinding
    private val viewModel: ComidaAdicionalViewModel by viewModels()
    private var comidaVm: ComidaVM = ComidaVM()
    private var comensalVm: ComensalVM = ComensalVM()

    companion object {
        fun newInstance() = ComidaAdicional()
    }

    //Animaciones
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaAdicionalBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtRegisComidaAdicional.startAnimation(slideInAnimationLeft)
        binding.imagComidaAdicional.startAnimation(slideInAnimatioRight)
        binding.txtNombreComensalComidaAdicional.startAnimation(slideInAnimationLeft)
        binding.linearLayoutDependiente.startAnimation(slideInAnimationLeft)
        binding.linearLayoutComensalAsociadoComida.startAnimation(slideInAnimatioRight)
        binding.linearLayoutSpnComida.startAnimation(slideInAnimationLeft)
        binding.btnAgregarAsociadoComida.startAnimation(slideInAnimationLeft)
        binding.btnIrPago.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    //Obtiene argumentos del costo total a pagar, actualiza el costo final y llama a la funcion registrarEventos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PagoDonativoArgs by navArgs()
        costeFinal += args.totalPorPagar
        registrarEventos()
    }

    //Llamada a funciones
    private fun registrarEventos() {
        descargarComensales()
        escanearQRDependiente()
        agregarComida()
        enviarComida()
        buscarComensalAsociado()
        escucharModificaciones()
    }

    //Cuando se realiza un cambio, se actualiza el texto
    private fun escucharModificaciones() {

        val inputDependiente = binding.inputDependienteComida
        inputDependiente.addTextChangedListener(object : TextWatcher {
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
                        "No hay un comensal con ese ID"
                    }
                    binding.txtNombreComensalComidaAdicional.text = newText
                }

                // Perform your operation here with the modified text
            }
        })
    }

    //Descarga una lista de comensales y los mapea
    private fun descargarComensales() {
        comensalVm.descargarComensales { comensales ->
            if(comensales != null){
                for(comensal in comensales){
                    mapaComensal[comensal.IdComensal] = "${comensal.nombres} ${comensal.apellidoPaterno} ${comensal.apellidoMatenro}"
                }
            }
        }
    }

    //Busca comensales asociados a un dependiente en funcion del ID del dependiente ingresado
    private fun buscarComensalAsociado() {
        binding.btnBuscarComensalAsociado.setOnClickListener{
            val idDependiente = binding.inputDependienteComida.text.toString().toInt()
 //           val nombreComensal = "Comensal: ${mapaComensal[idDependiente]}"
//            binding.txtNombreComensalComidaAdicional.text = nombreComensal
            mapaComensalAsociado.clear()
            comensalVm.descargarDependientes(idDependiente){ comensales ->
                if(comensales != null){
                    for(comensal in comensales){
                        val name = "${comensal.nombres} ${comensal.apellidoPaterno} ${comensal.apellidoMatenro}"
                        mapaComensalAsociado[name] = comensal.IdComensal
                    }
                    val listaComensalesAsociados = ArrayList(mapaComensalAsociado.keys)
                    if(listaComensalesAsociados.size == 0){
                        listaComensalesAsociados.add("No hay comensales asociados")
                    }
                    binding.spnComensalAsociadoComida.adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listaComensalesAsociados)
                }
            }
        }
    }

    //Registra las comidas en la base de datos y navega a la pantalla pago donativo, a la cual le pasa el costo final de las comidas adicionales
    private fun enviarComida() {
        binding.btnIrPago.setOnClickListener{
            Log.d("COMIDA ADICIONAL", "$listaComidas")
            comidaVm.registrarComidaDependiente(listaComidas)
            val accion = ComidaAdicionalDirections.actionComidaAdicionalToPagoDonativo(costeFinal)
            findNavController().navigate(accion)
        }

    }

    //Agrega una comida dependiente a la lista de comidas adicionales, calcula el valor de aportacion, sima el costo y agrega la comida
    private fun agregarComida() {
        binding.btnAgregarAsociadoComida.setOnClickListener {
            val prefs: SharedPreferences = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
            val defaultValue = "0"
            val idComedor = prefs.getString("IDComedor", defaultValue)?.toInt()

            val idDependiente = binding.inputDependienteComida.text.toString().toInt()
            val nombreComensalAsociado = binding.spnComensalAsociadoComida.selectedItem.toString()
            val idComensalAsociado = mapaComensalAsociado[nombreComensalAsociado]
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
            if(idComedor != null && idComensalAsociado != null){
                listaComidas.add(ComidaDependiente(idComedor, idDependiente, idComensalAsociado, aportacion, paraLlevar))
            }
        }

    }

    //Inicia la funcionalidad de escaneo de codigos QR para capturar el ID de un dependiente
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


}