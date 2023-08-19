package com.example.manager.data.information

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.example.manager.data.Convert
import com.example.manager.data.information.interfaces.InformationCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException

object DbFunctionsInformation {
    @SuppressLint("StaticFieldLeak")
    private val db : FirebaseFirestore = Firebase.firestore
    private const val col: String = "information"

    fun getInformation(id: Int, data: ArrayList<Information>, callback: InformationCallback){
        db.collection(col)
            .get()
            .addOnSuccessListener { result ->
                try {
                    for(document in result){
                        if(Convert.convertToInt(document.data["UserId"]) == id){
                            data.add(Information(
                                Convert.convertToInt(document.data["Id"]),
                                id,
                                Convert.convertToDouble(document.data["Value"]),
                                document.data["Title"].toString(),
                                document.data["Description"].toString(),
                                document.data["Date"].toString(),
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
}

