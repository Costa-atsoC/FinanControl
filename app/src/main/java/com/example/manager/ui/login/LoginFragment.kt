package com.example.manager.ui.login

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
import androidx.navigation.findNavController
import com.example.manager.R
import com.example.manager.data.DbFunctionsUser
import com.example.manager.data.interfaces.LoginCallback
import com.example.manager.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            DbFunctionsUser.checkUser(username, pass, object: LoginCallback {
                override fun onLoginSuccess() {
                    loading()
                    _binding!!.logWarn.text = "jberi"
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
    }

}