package com.example.sabordifapp.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.databinding.FragmentShowqrBinding
import com.example.sabordifapp.model.API.comensal.ComensalRegistrar
import com.example.sabordifapp.model.API.condicion.RegistroCondicion
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.CondicionVM
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class ComensalView : Fragment() {

    //binding
    private lateinit var binding: FragmentComensalBinding
    private lateinit var binding2: FragmentShowqrBinding
    private val viewModel: ComensalVM by viewModels()
    private val viewModel2: CondicionVM by viewModels()
    //private val viewModel3: ShowqrViewModel by viewModels()
    private var mapaCondiciones: MutableMap<String, Int> = mutableMapOf()

    companion object {
        fun newInstance() = ComensalView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComensalBinding.inflate(layoutInflater)
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtRegisComensal.startAnimation(slideInAnimationLeft)
        binding.nombreComensal.startAnimation(slideInAnimatioRight)
        binding.inputApellidoP.startAnimation(slideInAnimationLeft)
        binding.inputApellidoM.startAnimation(slideInAnimatioRight)
        binding.inputGenero.startAnimation(slideInAnimationLeft)
        binding.inputCURP.startAnimation(slideInAnimatioRight)
        binding.spnCondicion.startAnimation(slideInAnimationLeft)
        binding.btnRegistrar.startAnimation(slideInAnimatioRight)
        binding.btnescanearQR.startAnimation(slideInAnimationLeft)
        binding.textView3.startAnimation(slideInAnimatioRight)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validarCampos()
        condiciones()
    }

    private fun condiciones() {
        viewModel2.descargarCondiciones { List ->
            List?.forEach { objetoCondicion ->
                mapaCondiciones[objetoCondicion.nombreCondicion] = objetoCondicion.IdCondicion
            }
            val listaCondiciones = ArrayList(mapaCondiciones.keys)
            listaCondiciones.add(0, "Ninguna/No aplica")
            binding.spnCondicion.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                listaCondiciones
            )
        }
    }

    private fun obtenerGenero(genero: String): Int {
        val nGenero = genero.lowercase().filterNot { it.isWhitespace() }
        val num = when (nGenero) {
            "masculino" -> 1
            "femenino" -> 0
            else -> 2
        }
        return num
    }

    private fun validarCampos() {

        //verificarQR()
        escanearQR()

            binding.btnRegistrar.setOnClickListener {

                val nombreC = binding.inputNombre.text.toString()
                val apellidoP = binding.inputApellidoP.text.toString()
                val apellidoM = binding.inputApellidoM.text.toString()
                val curp = binding.inputCURP.text.toString()
                val genero = binding.inputGenero.text.toString()

                if(nombreC.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || curp.isEmpty()){
                    val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                    builder.setTitle("Error")
                        .setMessage("Por favor llene todos los campos")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }

                val condicion = binding.spnCondicion.selectedItem.toString()
                val datosComensal =
                    ComensalRegistrar(nombreC, apellidoP, apellidoM, curp, obtenerGenero(genero))


                viewModel.registrarNuevoComensal(datosComensal) { comensalId ->

                    if (comensalId != null) {
                        //Si se crashea es culpa de esto jiji

                        if(condicion != "Ninguna/No aplica"){
                            val idCondicion = mapaCondiciones.get(condicion)

                            if (idCondicion != null) {

                                val condicion = CondicionVM()
                                val listaCondiciones = listOf<RegistroCondicion>(
                                    RegistroCondicion(
                                        comensalId.idComensal,
                                        idCondicion
                                    )
                                )
                                condicion.registrarCondicion(listaCondiciones)
                            }

                        }
                        registrarObservables(comensalId.idComensal)
                    }
                }
                //val accion = ComensalViewDirections.actionComensalToRegistro()
                //findNavController().navigate(accion)
            }
        }

    private fun registrarObservables(idComensal:Int){
        val sharedPref = requireContext().getSharedPreferences(
            "mySharedPrefs",
            Context.MODE_PRIVATE
        )

        //val idComensal = sharedPref.getString("IDComensal", "")

        viewModel.idComensal2.observe(viewLifecycleOwner){ valor ->
            val editor = sharedPref.edit()
            editor.putString("IDComensal", idComensal.toString())
            editor.apply()

            val accionQR = ComensalViewDirections.actionComensalToShowqr2()
            findNavController().navigate(accionQR)
        }
    }

    private fun escanearQR()
    {
        Log.d("API_TEST", "Inicia QR")
        val escanearQR = GmsBarcodeScanning.getClient(requireContext())
        binding.btnescanearQR.setOnClickListener() {
            escanearQR.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue: String? = barcode.rawValue
                    val array = rawValue.toString().split("|")
                    val curp = array[0]
                    val apellidoP = array[2]
                    val apellidoM = array[3]
                    val nombreC = array[4]
                    val genero = array[5]
                    Log.d("API_TEST", "Este es el raw value ${rawValue}")
                    binding.inputCURP.setText(curp)
                    binding.inputApellidoP.setText(apellidoP)
                    binding.inputApellidoM.setText(apellidoM)
                    binding.inputNombre.setText(nombreC)
                    binding.inputGenero.setText(genero)
                }.addOnCanceledListener {
                    //Task cancelled
                }.addOnFailureListener { e ->
                    //Task failed with an exception
                }
        }

    }

    private fun mensajeError(error: String){
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
        builder.setTitle("Error")
            .setMessage(error)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun mensajeExitoso(exito: String){
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
        builder.setTitle("Acción exitosa")
            .setMessage(exito)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .show()
    }

}