package com.example.manager.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.manager.MenuFragment
import com.example.manager.R
import com.example.manager.ToPayFragment
import com.example.manager.ui.information.InformationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavBarChange(private var bottomNav: BottomNavigationView, private val fragmentManager: FragmentManager, private val containerViewId: Int) {

    init {
        nav()
    }

    private fun nav() {
            bottomNav.setOnItemSelectedListener { menuItem ->
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
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        transaction.commit()
    }
}
