package com.example.manager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.manager.R
import com.example.manager.databinding.FragmentInformationBinding
import com.example.manager.databinding.FragmentLoginBinding

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


}