package com.example.sabordifapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentInicioBinding
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.viewmodel.InicioViewModel

class Inicio : Fragment() {

    //View binding
    private lateinit var binding: FragmentInicioBinding
    private val viewModel: InicioViewModel by viewModels()
    private var mapaComedores: MutableMap<String, Int> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        registrarObservables()
    }

    private fun registrarObservables()
    {
        //viewModel.res.observe(viewLifecycleOwner){verificarAcc ->
        //}

        viewModel.listaComedores.observe(viewLifecycleOwner){listaC ->
            val arrCom = listaC.get(1)
            Log.e("API_TEST", "${arrCom}")
            //binding.idComedor.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrCom)
        }
    }

    private fun registrarEventos(){
        viewModel.descargarNombresComedor { List ->
            List?.forEach { objeto ->
                mapaComedores[objeto.nombreComedor] = objeto.IdComedor
            }
            val listita = ArrayList(mapaComedores.keys)
            binding.idComedor.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listita)
        }
        binding.btnIngresar.setOnClickListener {
            val tipo = binding.idComedor.selectedItem.toString()
            Log.d("Prueba", "boton para pasar a registro ${tipo}")
            val comedorSeleccionado = binding.idComedor.selectedItem
            val id = mapaComedores.get(comedorSeleccionado)?.toInt()
            val passw = binding.contraC.text.toString()

            val sharedPref = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("IDComedor", id.toString())
            editor.apply()

            if(id != null){
                val comedor = Responsable(id, passw)

                viewModel.validarLogin(comedor){loginValido ->
                    Log.d("API_TEST", "valor de log in valido ${loginValido?.access}")
                    if(loginValido?.access == 1){
                        val accion = InicioDirections.actionInicioToRegistro()
                        findNavController().navigate(accion)
                    }
                }
            }
        }
    }
}
