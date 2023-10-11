package com.example.sabordifapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sabordifapp.R
import com.example.sabordifapp.databinding.FragmentComensalBinding
import com.example.sabordifapp.viewmodel.ComensalViewModel

class ComensalView : Fragment() {

    private lateinit var binding:FragmentComensalBinding

    companion object {
        fun newInstance() = ComensalView()
    }

    private lateinit var viewModel: ComensalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comensal, container, false)
    }

}