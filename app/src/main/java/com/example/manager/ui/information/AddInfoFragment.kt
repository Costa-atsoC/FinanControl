package com.example.manager.ui.information

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.findNavController
import com.example.manager.R
import com.example.manager.databinding.FragmentAddInfoBinding
import com.example.manager.databinding.FragmentInformationBinding

class AddInfoFragment : Fragment() {
    private var _binding: FragmentAddInfoBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddInfoBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var statusWin: Boolean

        _binding!!.addBack.setOnClickListener{
            view.findNavController().navigate(R.id.action_addInfoFragment_to_informationFragment)
        }

        _binding!!.addWin.setOnCheckedChangeListener { _, isChecked ->
            statusWin = isChecked
            switchMode(statusWin)
        }
    }

    private fun switchMode(statusWin: Boolean){
        _binding!!.addGroceries.isEnabled = statusWin
        _binding!!.addDrink.isEnabled = statusWin
        _binding!!.addOther.isEnabled = statusWin

        var radioButton : RadioButton
        _binding!!.addCategories.setOnCheckedChangeListener { _, checkedId ->
            radioButton = requireView().findViewById(checkedId)
            radioButton.isSelected = statusWin
        }
    }

}