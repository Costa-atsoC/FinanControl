package com.example.manager.ui.information

import SpacingItemDecoration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

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
        _binding!!.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.list -> {
                    loadFragment(InformationFragment())
                    true
                }
                R.id.toPay -> {
                    loadFragment(ToPayFragment())
                    true
                }
                R.id.menu -> {
                    loadFragment(MenuFragment())
                    true
                }
                else -> false
            }
        }
        getInformation()

        _binding!!.infoAdd.setOnClickListener {
            try {
                view.findNavController().navigate(R.id.action_informationFragment_to_addInfoFragment)
            } catch (e: Exception) {
                Log.e("Navigation", "Navigation error: ${e.message}")
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction!!.replace(R.id.nav_host_fragment_content_main, fragment)
        transaction.commit()
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
        val trueData: ArrayList<Information> = ArrayList()
        val warn = _binding!!.infoWarn

        DbFunctionsInformation.getInformation(sharedViewModel.currentUser.id, data, object : InformationCallback{
            override fun onInformationFound() {
                //Sorting the data by date so it appears corresponding to the actual time
                data.sortByDescending { it.date }
                if(data.size > 0){

                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    //We add a fake data with only the data so we can do the date
                    for(i in 0 until data.size){
                        if(!trueData.any{ it.id == -1 && it.date == data[i].date.split(" ")[0]}){
                            trueData.add(Information(date = data[i].date.split(" ")[0]))
                        }
                        trueData.add(data[i])
                    }

                }
                val recycler: RecyclerView = _binding!!.infoRecycler
                requireActivity().runOnUiThread {
                    recycler.layoutManager = LinearLayoutManager(requireContext())

                    val adapter = CustomAdapterInformation(trueData, requireContext())
                    recycler.adapter = adapter

                    // Add spacing between items using ItemDecoration
                    recycler.addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.itemRecycler)))

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