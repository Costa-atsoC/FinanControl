package com.example.manager.data.user.interfaces

import com.example.manager.data.user.User

interface LoginCallback {
    fun onLoginSuccess()
    fun onLoginNotFound()
    fun onLoginError()
}