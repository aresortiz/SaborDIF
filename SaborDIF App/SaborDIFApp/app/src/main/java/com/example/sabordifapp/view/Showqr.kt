package com.example.sabordifapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sabordifapp.R
import com.example.sabordifapp.viewmodel.ShowqrViewModel
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
        val slideInAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left)
        val slideInAnimatioRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right)
        binding.txtcodigoQR.startAnimation(slideInAnimationLeft)
        binding.qrComensal.startAnimation(slideInAnimatioRight)
        binding.btnRegresar.startAnimation(slideInAnimationLeft)
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

        binding.btnRegresar.setOnClickListener{
            val accion = ShowqrDirections.actionShowqr2ToRegistro()
            findNavController().navigate(accion)
        }
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