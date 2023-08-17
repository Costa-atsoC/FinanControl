package com.example.manager.data.interfaces

interface LoginCallback {
    fun onLoginSuccess()
    fun onLoginNotFound()
    fun onLoginError()
}