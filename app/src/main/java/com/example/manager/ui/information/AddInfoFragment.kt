package com.example.manager.ui.information

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.manager.R
import com.example.manager.data.Convert.convertToDouble
import com.example.manager.data.information.DbFunctionsInformation
import com.example.manager.data.information.Information
import com.example.manager.data.information.interfaces.AddInformationCallback
import com.example.manager.data.user.SharedViewModel
import com.example.manager.databinding.FragmentAddInfoBinding
import showCustomToast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInfoFragment : Fragment() {
    private var _binding: FragmentAddInfoBinding? = null
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }
    private var methode = ""
    var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddInfoBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var radioButton : RadioButton

        _binding!!.addBack.setOnClickListener{
            view.findNavController().navigate(R.id.action_addInfoFragment_to_informationFragment)
        }

        //If the user select the lose it needs the categories
        _binding!!.addLose.setOnCheckedChangeListener { _, isChecked ->
            Log.d("isChecked", isChecked.toString())
            switchMode(isChecked)
            enableBtn()
        }

        _binding!!.addTitle.addTextChangedListener{
            enableBtn()
        }
        _binding!!.addDescription.addTextChangedListener{
            enableBtn()
        }
        _binding!!.addValue.addTextChangedListener{
            enableBtn()
        }
        _binding!!.addMethode.setOnCheckedChangeListener{_, checkedId ->
            enableBtn()
            radioButton = view.findViewById(checkedId)
            methode = radioButton.text.toString()
        }

        _binding!!.addCategories.setOnCheckedChangeListener { _, checkedId ->
            enableBtn()
            radioButton = view.findViewById(checkedId)
            category = radioButton.text.toString()
        }

        _binding!!.addBtn.setOnClickListener {
            addInformation()
        }
    }

    private fun switchMode(statusWin: Boolean){
        _binding?.apply {
            addGroceries.isEnabled = statusWin
            addDrink.isEnabled = statusWin
            addOther.isEnabled = statusWin

            var radioButton : RadioButton
            addCategories.setOnCheckedChangeListener { _, checkedId ->
                radioButton = requireView().findViewById(checkedId)
                radioButton.isSelected = statusWin
            }
        }
    }

    private fun enableBtn(){
        _binding?.apply {
            val isTitleNotEmpty = addTitle.text.isNotEmpty()
            val isDescriptionNotEmpty = addDescription.text.isNotEmpty()
            val isMethodSelected = addMethode.checkedRadioButtonId != -1
            val isValueNotEmpty = addValue.text.isNotEmpty()

            addBtn.isEnabled = when {
                addLose.isChecked -> addCategories.checkedRadioButtonId != -1 && isTitleNotEmpty && isDescriptionNotEmpty && isMethodSelected && isValueNotEmpty
                else -> isTitleNotEmpty && isDescriptionNotEmpty && isMethodSelected && isValueNotEmpty
            }

        }

    }

    private fun addInformation(){
        val genId = (0..999999999).random()
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val setting = arrayListOf(methode, category)
        DbFunctionsInformation.addInformation(
            Information(
                genId,
                sharedViewModel.currentUser.id,
                convertToDouble(_binding!!.addValue.text.toString()),
                _binding!!.addTitle.text.toString(),
                _binding!!.addDescription.text.toString(),
                currentDate,
                true,
                setting
            ), object : AddInformationCallback{
                override fun onAddInformationAdded() {
                    Toast(requireContext()).showCustomToast(getString(R.string.addedInfo), requireActivity())
                    view?.findNavController()?.navigate(R.id.action_addInfoFragment_to_informationFragment)
                }

                override fun onAddInformationError() {
                    Toast(requireContext()).showCustomToast(getString(R.string.error), requireActivity())
                }
            }
        )
    }

}