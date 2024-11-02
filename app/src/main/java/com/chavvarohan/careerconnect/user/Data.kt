package com.chavvarohan.careerconnect.user

data class Data(
    var companyName: String,
    var description: String,
    var link: String,
    var date: String,
    var regLink :String,
    var timestamp: Long = System.currentTimeMillis()
)
