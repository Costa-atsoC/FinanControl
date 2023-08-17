package com.example.manager.data.interfaces

interface RegisterCallback {
    fun onRegisterSuccess()
    fun onRegisterUserExists()
    fun onRegisterError()
}