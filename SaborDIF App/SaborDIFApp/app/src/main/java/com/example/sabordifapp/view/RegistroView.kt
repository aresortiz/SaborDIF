package com.example.sabordifapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentRegistroBinding
import com.example.sabordifapp.viewmodel.RegistroViewModel

class RegistroView : Fragment() {

    //binding
    private lateinit var binding: FragmentRegistroBinding


    companion object {
        fun newInstance() = RegistroView()
    }

    private lateinit var viewModel: RegistroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }


}