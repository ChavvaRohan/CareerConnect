package com.chavvarohan.careerconnect

data class Info(
    var image: String = "",
    var title: String = "",
    var date: String = "",
    var description: String = "",
    var link: String = "",
    var timestamp: Long = System.currentTimeMillis()
)
