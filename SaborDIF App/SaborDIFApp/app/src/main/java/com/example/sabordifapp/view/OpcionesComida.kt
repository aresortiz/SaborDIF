package com.example.sabordifapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sabordifapp.viewmodel.OpcionesComidaViewModel
import com.example.sabordifapp.R

class OpcionesComida : Fragment() {

    companion object {
        fun newInstance() = OpcionesComida()
    }

    private lateinit var viewModel: OpcionesComidaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_opciones_comida, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpcionesComidaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}