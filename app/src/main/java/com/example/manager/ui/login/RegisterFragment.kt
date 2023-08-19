package com.example.manager.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.example.manager.R
import com.example.manager.data.user.DbFunctionsUser
import com.example.manager.data.user.User
import com.example.manager.data.user.interfaces.RegisterCallback
import com.example.manager.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    private fun registerUser(name: String, email: String, pass: String, confPass: String, gender: String){
        if(pass == confPass){
            val genId = (0..999999999).random()
            val newUser = User(genId, name, email, pass, gender)
            println(newUser)
            DbFunctionsUser.addUser(newUser, object : RegisterCallback {
                override fun onRegisterSuccess() {
                    view!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                override fun onRegisterUserExists() {
                    loading()
                    _binding!!.regWarn.text = getString(R.string.already)
                }

                override fun onRegisterError() {
                    loading()
                    _binding!!.regWarn.text = getString(R.string.error)
                }

            })
        }
    }

    private fun loading(){
        val btn = _binding!!.regBtn
        val log = _binding!!.regLoad
        if(btn.visibility == View.VISIBLE){
            btn.visibility = View.INVISIBLE
            btn.isEnabled = false
            log.visibility = View.VISIBLE
        }else {
            btn.visibility = View.VISIBLE
            btn.isEnabled = true
            log.visibility = View.INVISIBLE
        }
    }

    private fun unlockBtn(){
        _binding!!.regBtn.isEnabled =
            _binding!!.regUser.text.isNotEmpty()
                    && _binding!!.regPass.text.isNotEmpty()
                    && _binding!!.regConfPass.text.isNotEmpty()
                    && _binding!!.regGender.checkedRadioButtonId != -1
                    && _binding!!.regEmail.text.isNotEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding!!.regUser.addTextChangedListener  {
            unlockBtn()
        }

        _binding!!.regEmail.addTextChangedListener{
            unlockBtn()
        }

        _binding!!.regPass.addTextChangedListener  {
            unlockBtn()
        }

        _binding!!.regConfPass.addTextChangedListener  {
            unlockBtn()
        }

        var radioButton : RadioButton = _binding!!.regOther

        _binding!!.regGender.setOnCheckedChangeListener { _, checkedId ->
            unlockBtn()
            radioButton = view.findViewById(checkedId)
        }

        //Btn to register
        _binding!!.regBtn.setOnClickListener{
            loading()
            registerUser(
                _binding!!.regUser.text.toString(),
                _binding!!.regEmail.text.toString(),
                _binding!!.regPass.text.toString(),
                _binding!!.regConfPass.text.toString(),
                radioButton.text.toString()
            )
        }

        _binding!!.regBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}