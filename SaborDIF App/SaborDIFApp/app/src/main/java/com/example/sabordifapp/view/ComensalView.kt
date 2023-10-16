package com.example.sabordifapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.print.PrintJobInfo.STATE_CANCELED
import android.print.PrintJobInfo.STATE_COMPLETED
import android.print.PrintJobInfo.STATE_FAILED
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.ShowqrViewModel
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.databinding.FragmentRegistroBinding
import com.example.sabordifapp.databinding.FragmentShowqrBinding
import com.example.sabordifapp.model.API.comensal.ComensalRegistrar
import com.example.sabordifapp.model.API.condicion.RegistroCondicion
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.ComensalVM
import com.example.sabordifapp.viewmodel.APIVM.viewmodel.CondicionVM
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

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

    private fun registrarEventos() {

        verificarQR()
        escanearQR()

            binding.btnRegistrar.setOnClickListener {

                val nombreC = binding.inputNombre.text.toString()
                val apellidoP = binding.inputApellidoP.text.toString()
                val apellidoM = binding.inputApellidoM.text.toString()
                val curp = binding.inputCURP.text.toString()
                val genero = binding.inputGenero.text.toString()
                //Si aplica
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

    private fun verificarQR()
    {

        val moduleInstallClient = ModuleInstall.getClient(requireContext())
        val optionalModuleApi = GmsBarcodeScanning.getClient(requireContext())

        moduleInstallClient
            .areModulesAvailable(optionalModuleApi)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    println("<Si está instalado")
                    escanearQR()
                } else {
                    println("No está instalado")
                    instalarModulo()
                }
            }
            .addOnFailureListener {
                println("Error al verificar módulo")
            }

    }

    private fun instalarModulo() {
        val moduleInstallClient = ModuleInstall.getClient(requireContext())
        val optionalModuleApi = GmsBarcodeScanning.getClient(requireContext())

        val moduleInstallRequest =
            ModuleInstallRequest.newBuilder()
                .addApi(optionalModuleApi)
                .setListener(listener)
                .build()

        moduleInstallClient
            .installModules(moduleInstallRequest)
            .addOnSuccessListener {
                if (it.areModulesAlreadyInstalled()) {
                    // Modules are already installed when the request is sent.
                    println("I N S T A L A N D O ......")
                }
            }
            .addOnFailureListener {
                println("NO SE PUEDE INSTALAR")

            }
    }

    inner class ModuleInstallProgressListener : InstallStatusListener {
        override fun onInstallStatusUpdated(update: ModuleInstallStatusUpdate) {
            // Progress info is only set when modules are in the progress of downloading.
            update.progressInfo?.let {
                val progress = (it.bytesDownloaded * 100 / it.totalBytesToDownload).toInt()
                // Set the progress for the progress bar.
                //progressBar.setProgress(progress)
                println(progress)
                if(progress == 100)
                {
                    Log.d("API_TEST", "Modulo Instalado")

                }
            }

            if (isTerminateState(update.installState)) {
                //moduleInstallClient.unregisterListener(this)
            }
        }

        fun isTerminateState(@ModuleInstallStatusUpdate.InstallState state: Int): Boolean {
            return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED
        }
    }

    val listener = ModuleInstallProgressListener()


}