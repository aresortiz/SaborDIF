package com.example.sabordifapp.view

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComidaBinding
import com.example.sabordifapp.model.API.comida.Comida
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComidaVM
import com.example.sabordifapp.viewmodel.ComidaViewModel
import kotlin.math.cos

class Comida : Fragment() {

    //binding
    var costeFinal = 0
    private lateinit var binding: FragmentComidaBinding
    private val viewModel:ComidaViewModel by viewModels()
    private val comidaVm: ComidaVM = ComidaVM()
    private val comensalVm: ComensalVM = ComensalVM()
    private var mapaComensal: MutableMap<Int, String> = mutableMapOf()

    companion object {
        fun newInstance() = ComensalView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtRegisComida.startAnimation(slideInAnimationLeft)
        binding.imagComida.startAnimation(slideInAnimatioRight)
        binding.txtComensalComida.startAnimation(slideInAnimationLeft)
        binding.linearLayoutComensalComida.startAnimation(slideInAnimatioRight)
        binding.linearLayoutSpnPagos.startAnimation(slideInAnimationLeft)
        binding.txtComidaAdicional.startAnimation(slideInAnimatioRight)
        binding.btnSi.startAnimation(slideInAnimationLeft)
        binding.btnNo.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        escucharModificaciones()
        registrarComidaAdicional()
        registrarComida()
        obtenerComensales()
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

    private fun escucharModificaciones() {
        val inputComensal = binding.inputIDComensalComida
        inputComensal.addTextChangedListener(object : TextWatcher {
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
                    binding.txtComensalComida.text = newText
                }

                // Perform your operation here with the modified text
            }
        })
    }

    private fun registrarComidaAdicional() {
        binding.btnSi.setOnClickListener {
            //val comedorRegistrado = binding.btnRegistrar.isSelected.toString()
            enviarRegistroComida()
            val accion = ComidaDirections.actionComidaToComidaAdicional(costeFinal)
            findNavController().navigate(accion)
        }
    }

    private fun registrarComida(){
        binding.btnNo.setOnClickListener {
            enviarRegistroComida()
            val accion = ComidaDirections.actionComidaToPagoDonativo(costeFinal)
            findNavController().navigate(accion)
        }
    }

    private fun enviarRegistroComida(){
        val prefs: SharedPreferences = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
        val defaultValue = "0"
        val idComedor = prefs.getString("IDComedor", defaultValue)?.toInt()

        var idComensal = binding.inputIDComensalComida.text.toString().toInt()

        val valorPago = binding.spnPagoComidaComensal.selectedItem.toString()
        val valorSitio = binding.spnParaLlevarComidaComensal.selectedItem.toString()
        var aportacion = 0
        if(valorPago[0] == 'P'){
            aportacion= 13
        }
        var paraLlevar = 0
        if(valorSitio[0] == 'P'){
            paraLlevar = 1
        }
        costeFinal += aportacion
        if(idComedor != null){
            comidaVm.registrarComida(com.example.sabordifapp.model.API.comida.Comida(idComensal, idComedor, aportacion, paraLlevar))
        }

    }


}