package com.example.manager.ui.login

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.manager.R
import com.example.manager.data.user.DbFunctionsUser
import com.example.manager.data.interfaces.LoginCallback
import com.example.manager.data.user.SharedViewModel
import com.example.manager.data.user.SharedViewModel.Pref
import com.example.manager.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //The companion object can be found on the SharedViewModel for the user
        val pref = requireContext().getSharedPreferences(Pref.File, Context.MODE_PRIVATE)
        sharedViewModel.setCurrentUser(
            pref.getInt(Pref.Id, -1),
            pref.getString(Pref.Name, "")!!,
            pref.getString(Pref.Email, "")!!,
            pref.getString(Pref.Password, "")!!,
            pref.getString(Pref.Gender, "")!!
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onStop() {
        super.onStop()
        _binding!!.logUser.setText("")
        _binding!!.logPass.setText("")
        _binding!!.logWarn.text = ""
    }

    private fun loading(){
        val btn = _binding!!.logBtn
        val log = _binding!!.logLoading
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
        _binding!!.logBtn.isEnabled = _binding!!.logUser.text.isNotEmpty() && _binding!!.logPass.text.isNotEmpty()
    }

    private fun saveUserCredentials(){
        val pref = requireContext().getSharedPreferences(Pref.File, Context.MODE_PRIVATE)
        val edit = pref.edit()

        //Saving the user data
        edit.putInt(Pref.Id, sharedViewModel.currentUser.id)
        edit.putString(Pref.Name, sharedViewModel.currentUser.name)
        edit.putString(Pref.Password, sharedViewModel.currentUser.password)
        edit.putString(Pref.Email, sharedViewModel.currentUser.email)
        edit.putString(Pref.Gender, sharedViewModel.currentUser.gender)

        edit.apply()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(sharedViewModel.currentUser.id == -1) {
            super.onViewCreated(view, savedInstanceState)

            val textView = _binding!!.logToReg
            val text = getString(R.string.regText)
            val spannableString = SpannableString(text)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }

            spannableString.setSpan(clickableSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            textView.text = spannableString
            textView.movementMethod = LinkMovementMethod.getInstance()

            _binding!!.logUser.addTextChangedListener {
                unlockBtn()
            }

            _binding!!.logPass.addTextChangedListener {
                unlockBtn()
            }

            _binding!!.logBtn.setOnClickListener {
                loading()
                val username = _binding!!.logUser.text.toString()
                val pass = _binding!!.logPass.text.toString()
                DbFunctionsUser.checkUser(username, pass, sharedViewModel, object : LoginCallback {
                    override fun onLoginSuccess() {
                        loading()
                        saveUserCredentials()
                        view.findNavController()
                            .navigate(R.id.action_loginFragment_to_informationFragment)
                    }

                    override fun onLoginNotFound() {
                        loading()
                        _binding!!.logWarn.text = getString(R.string.notFound)
                    }

                    override fun onLoginError() {
                        loading()
                        _binding!!.logWarn.text = getString(R.string.error)
                    }
                })
            }
        }else{
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_informationFragment)
        }
    }

}