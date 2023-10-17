package com.example.sabordifapp.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.print.PrintJobInfo
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentInicioBinding
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.viewmodel.InicioViewModel
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

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
        val slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        binding.linearLayoutInicioSesion.startAnimation(slideInAnimation)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        registrarObservables()
        verificarQR()
        instalarModulo()
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
                    }else{
                        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                        builder.setTitle("Inicio de sesión inválido")
                            .setMessage("La contraseña ingresada es incorrecta, por favor ingrésela nuevamente")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
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
                    //escanearQR()
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
            return state == PrintJobInfo.STATE_CANCELED || state == PrintJobInfo.STATE_COMPLETED || state == PrintJobInfo.STATE_FAILED
        }
    }

    val listener = ModuleInstallProgressListener()
}
