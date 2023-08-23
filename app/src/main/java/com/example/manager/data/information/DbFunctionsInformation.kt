package com.example.manager.data.information

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.example.manager.data.Convert
import com.example.manager.data.information.interfaces.AddInformationCallback
import com.example.manager.data.information.interfaces.InformationCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DbFunctionsInformation {
    @SuppressLint("StaticFieldLeak")
    private val DB : FirebaseFirestore = Firebase.firestore
    private const val COL: String = "information"

    fun getInformation(id: Int, data: ArrayList<Information>, callback: InformationCallback){
        DB.collection(COL)
            .get()
            .addOnSuccessListener { result ->
                try {
                    for(document in result){
                        if(Convert.convertToInt(document.data["UserId"]) == id && document.data["Payed"].toString().toBoolean()){
                            data.add(Information(
                                Convert.convertToInt(document.data["Id"]),
                                id,
                                Convert.convertToDouble(document.data["Value"].toString()),
                                document.data["Title"].toString(),
                                document.data["Description"].toString(),
                                document.data["Date"].toString(),
                                document.data["Payed"].toString().toBoolean(),
                                Convert.convertToArrayList(document.data["Settings"])
                            ))
                        }
                    }
                    if(data.isNotEmpty()){
                        callback.onInformationFound()
                        return@addOnSuccessListener
                    }
                    callback.onInformationNotFound()
                    return@addOnSuccessListener
                }catch (e :IOException){
                    callback.onInformationError()
                    return@addOnSuccessListener
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
                callback.onInformationError()
                return@addOnFailureListener
            }
    }

    fun addInformation(data: Information, callback: AddInformationCallback){
        if(data.settings.contains("")){
            data.settings.remove("")
        }
        val information = hashMapOf(
            "Id" to data.id,
            "UserId" to data.userId,
            "Value" to data.value,
            "Title" to data.title,
            "Description" to data.description,
            "Date" to data.date,
            "Payed" to data.payed,
            "Settings" to data.settings
        )

        DB.collection(COL)
            .add(information)
            .addOnSuccessListener {
                try {
                    callback.onAddInformationAdded()
                    return@addOnSuccessListener
                }catch (e: IOException){
                    callback.onAddInformationError()
                    return@addOnSuccessListener
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
                callback.onAddInformationError()
                return@addOnFailureListener
            }
    }
}

