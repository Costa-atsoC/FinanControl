package com.example.manager.data.user

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var currentUser : User = User()

    fun setCurrentUser(id: Int, name: String, email: String, password: String, gender: String){
        currentUser.id = id
        currentUser.name = name
        currentUser.email = email
        currentUser.password = password
        currentUser.gender = gender
    }

    fun logOut(){
        currentUser = User()
    }
    companion object Pref{
        const val File: String = "LoginUser"
        const val Id: String = "Id"
        const val Name: String = "Name"
        const val Email: String = "Email"
        const val Password: String = "Password"
        const val Gender: String = "Gender"
    }
}

