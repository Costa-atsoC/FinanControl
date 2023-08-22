package com.example.manager.data

object Convert {
    fun convertToInt(value: Any?): Int {
        return when (value) {
            is Int -> value
            is Double -> value.toInt()
            is Float -> value.toInt()
            is Long -> value.toInt()
            is Short -> value.toInt()
            is Byte -> value.toInt()
            is String -> value.toInt()
            else -> {-1}
        }
    }

    fun convertToDouble(value: Any?): Double {
        return when (value) {
            is Int -> value.toDouble()
            is Double -> value
            is Float -> value.toDouble()
            is Long -> value.toDouble()
            is Short -> value.toDouble()
            is Byte -> value.toDouble()
            is String -> value.toDouble()
            else -> {-1.0}
        }
    }
    fun convertToArrayList (value: Any?) : ArrayList<String>{
        return when(value){
            is ArrayList<*> -> {
                val stringArrayList = ArrayList<String>()
                for (item in value) {
                    if (item is String) {
                        stringArrayList.add(item)
                    }
                }
                stringArrayList
            }
            else -> {
                ArrayList()
            }
        }
    }
}