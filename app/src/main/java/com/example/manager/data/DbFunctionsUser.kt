package com.example.manager.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.example.manager.data.interfaces.LoginCallback
import com.example.manager.data.interfaces.RegisterCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DbFunctionsUser {
    @SuppressLint("StaticFieldLeak")
    private val db : FirebaseFirestore = Firebase.firestore
    private const val col: String = "users"

    fun addUser(data: User, callback: RegisterCallback){
        val hashedPassword = BCrypt.hashpw(data.password, BCrypt.gensalt())
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val user = hashMapOf(
            "Id" to data.id,
            "Name" to data.name,
            "Email" to data.email,
            "Password" to hashedPassword,
            "Gender" to data.gender,
            "Start" to currentDate
        )

        db.collection(col)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.data["Name"] == data.name || document.data["Email"] == data.email){
                        callback.onRegisterUserExists()
                        return@addOnSuccessListener
                    }
                }
                db.collection(col)
                    .add(user)
                    .addOnSuccessListener { _ ->
                        callback.onRegisterSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        callback.onRegisterError()
                        return@addOnFailureListener
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                callback.onRegisterError()
                return@addOnFailureListener
            }


    }

    fun checkUser(name: String, pass: String, callback: LoginCallback){
        db.collection(col)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.data["Name"] == name && BCrypt.checkpw(pass, document.data["Password"].toString())){
                        callback.onLoginSuccess()
                        return@addOnSuccessListener
                    }
                }
                callback.onLoginNotFound()
                return@addOnSuccessListener
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                callback.onLoginError()
                return@addOnFailureListener
            }
    }
}