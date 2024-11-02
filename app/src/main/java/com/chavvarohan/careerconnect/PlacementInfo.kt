package com.chavvarohan.careerconnect

data class PlacementInfo(
    var company: String = "",
    var date: String = "",
    var description: String = "",
    var link: String = "",
    var timestamp: Long = System.currentTimeMillis()
)
