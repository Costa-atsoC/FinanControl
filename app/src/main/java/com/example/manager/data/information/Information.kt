package com.example.manager.data.information


data class Information(
    var id: Int = -1,
    var userId: Int = -1,
    var value: Double = 0.0,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var payed: Boolean = true,
    var settings: ArrayList<String> = ArrayList()
    )
