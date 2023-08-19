package com.example.manager.data.user.interfaces

interface RegisterCallback {
    fun onRegisterSuccess()
    fun onRegisterUserExists()
    fun onRegisterError()
}