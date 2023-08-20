package com.example.manager.ui.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manager.MenuFragment
import com.example.manager.R
import com.example.manager.ToPayFragment
import com.example.manager.data.information.DbFunctionsInformation
import com.example.manager.data.information.Information
import com.example.manager.data.information.interfaces.InformationCallback
import com.example.manager.data.user.SharedViewModel
import com.example.manager.databinding.FragmentInformationBinding
import com.example.manager.ui.NavBarChange
import com.google.android.material.bottomnavigation.BottomNavigationView

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarChange(_binding!!.bottomNav, parentFragmentManager, R.id.nav_host_fragment_content_main)
        getInformation()

        _binding!!.infoAdd.setOnClickListener {
            view.findNavController().navigate(R.id.action_informationFragment_to_addInfoFragment)
        }
    }

    private fun loading(){
        val load = _binding!!.infoLoad
        val recycler = _binding!!.infoRecycler
        val warn = _binding!!.infoWarn
        val add = _binding!!.infoAdd
        if(load.visibility == View.VISIBLE){
            recycler.visibility = View.VISIBLE
            warn.visibility = View.VISIBLE
            add.visibility = View.VISIBLE
            load.visibility = View.INVISIBLE
        }else {
            recycler.visibility = View.INVISIBLE
            warn.visibility = View.INVISIBLE
            add.visibility = View.INVISIBLE
            load.visibility = View.VISIBLE
        }
    }

    private fun getInformation(){
        val data: ArrayList<Information> = ArrayList()
        val warn = _binding!!.infoWarn
        DbFunctionsInformation.getInformation(sharedViewModel.currentUser.id, data, object : InformationCallback{
            override fun onInformationFound() {
                val recycler: RecyclerView = _binding!!.infoRecycler
                requireActivity().runOnUiThread {
                    recycler.layoutManager = LinearLayoutManager(requireContext())

                    val adapter = CustomAdapterInformation(data)
                    recycler.adapter = adapter
                }
                loading()

            }

            override fun onInformationError() {
                warn.text = getString(R.string.errorInfo)
                loading()

            }

            override fun onInformationNotFound() {
                warn.text = getString(R.string.noInfo)
                loading()

            }
        })
    }
}