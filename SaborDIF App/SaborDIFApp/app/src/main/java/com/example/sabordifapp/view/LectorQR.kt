package com.example.sabordifapp.view

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.databinding.FragmentComidaBinding
import com.example.sabordifapp.databinding.FragmentDependienteBinding
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class LectorQR : AppCompatActivity() {
    //Iniciar binding y el Scan
    private lateinit var binding: FragmentComensalBinding
    private lateinit var binding2: FragmentDependienteBinding
    private lateinit var binding3: FragmentComidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentComensalBinding.inflate(layoutInflater)
        binding2 = FragmentDependienteBinding.inflate(layoutInflater)
        binding3 = FragmentComidaBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_pag_leer_qr)
        setContentView(binding.root)

        //validarModulo()
    }
}
