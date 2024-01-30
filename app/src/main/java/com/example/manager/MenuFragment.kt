package com.example.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.manager.databinding.FragmentInformationBinding
import com.example.manager.databinding.FragmentMenuBinding
import com.example.manager.ui.NavBarChange
import com.example.manager.ui.information.InformationFragment

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
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
        }    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction!!.replace(R.id.nav_host_fragment_content_main, fragment)
        transaction.commit()
    }

}