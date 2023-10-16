package com.example.sabordifapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sabordifapp.databinding.FragmentShowqrBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class Showqr : Fragment() {

    private lateinit var binding: FragmentShowqrBinding
    private val viewModel : ShowqrViewModel by viewModels()

    companion object {
        fun newInstance() = Showqr()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowqrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        val sharedPrefs = requireContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE)
        val idComensal = sharedPrefs.getString("IDComensal", "")
        val qr = generarQR(idComensal.toString())
        binding.qrComensal.setImageBitmap(qr)
    }

    private fun generarQR(curp: String) : Bitmap?{
        val codigoQR = QRCodeWriter()

        try{
            val matrizQR : BitMatrix = codigoQR.encode(curp, BarcodeFormat.QR_CODE, 512, 512)
            val anchoMatriz = matrizQR.width
            val altoMatriz = matrizQR.height

            val bitmapQR = Bitmap.createBitmap(anchoMatriz, altoMatriz, Bitmap.Config.RGB_565)
            for(x in 0 until anchoMatriz){
                for(y in 0 until altoMatriz){
                    bitmapQR.setPixel(x,y, if (matrizQR[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmapQR
        }catch (e: WriterException){
            e.printStackTrace()
            return null
        }
    }

}