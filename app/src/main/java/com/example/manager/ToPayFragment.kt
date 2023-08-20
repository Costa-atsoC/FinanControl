package com.example.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.manager.databinding.FragmentInformationBinding
import com.example.manager.databinding.FragmentToPayBinding
import com.example.manager.ui.NavBarChange

class ToPayFragment : Fragment() {
    private var _binding: FragmentToPayBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentToPayBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarChange(_binding!!.bottomNav, parentFragmentManager, R.id.nav_host_fragment_content_main)
    }
}