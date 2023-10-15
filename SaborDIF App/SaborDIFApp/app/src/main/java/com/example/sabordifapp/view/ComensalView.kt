package com.example.sabordifapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.model.API.comensal.ComensalRegistrar
import com.example.sabordifapp.model.API.condicion.Condicion
import com.example.sabordifapp.model.API.condicion.RegistroCondicion
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.CondicionVM
import com.example.sabordifapp.viewmodel.ComensalViewModel

class ComensalView : Fragment() {

    //binding
    private lateinit var binding: FragmentComensalBinding
    private val viewModel:ComensalVM by viewModels()
    private val viewModel2:CondicionVM by viewModels()
    private var mapaCondiciones: MutableMap<String, Int> = mutableMapOf()

    companion object {
        fun newInstance() = ComensalView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComensalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        condiciones()
    }

    private fun condiciones() {
        viewModel2.descargarCondiciones { List ->
            List?.forEach { objetoCondicion ->
                mapaCondiciones[objetoCondicion.nombreCondicion] = objetoCondicion.IdCondicion
            }
            val listaCondiciones = ArrayList(mapaCondiciones.keys)
            binding.spnCondicion.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaCondiciones)
        }
    }

    private fun registrarEventos() {

        binding.btnRegistrar.setOnClickListener {
            val nombreC = binding.inputNombre.text.toString()
            val apellidoP = binding.inputApellidoP.text.toString()
            val apellidoM = binding.inputApellidoM.text.toString()
            val curp = binding.inputCURP.text.toString()
            val genero = binding.inputGenero.text.toString()
            //Si aplica
            val condicion = binding.spnCondicion.selectedItem.toString()
            val datosComensal = ComensalRegistrar(nombreC, apellidoP, apellidoM, curp, obtenerGenero(genero))
            viewModel.registrarNuevoComensal(datosComensal){ comensalId ->

                if(comensalId != null){
                    //Si se crashea es culpa de esto jiji
                    val sharedPref = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("IDComensal", comensalId.idComensal.toString())
                    editor.apply()
                    val idCondicion = mapaCondiciones.get(condicion)?.toInt()

                    if(idCondicion != null){

                        val condicion = CondicionVM()
                        val listaCondiciones = listOf<RegistroCondicion>(RegistroCondicion(comensalId.idComensal, idCondicion))
                        condicion.registrarCondicion(listaCondiciones)
                    }
                }
        }
            val accion = ComensalViewDirections.actionComensalToRegistro()
            findNavController().navigate(accion)
        }
    }

    private fun obtenerGenero(genero: String) : Int {
        val nGenero = genero.lowercase().filterNot { it.isWhitespace() }
        val num = when(nGenero) {
            "masculino" -> 1
            "femenino" -> 0
            else -> 2
        }
        return num
    }

}