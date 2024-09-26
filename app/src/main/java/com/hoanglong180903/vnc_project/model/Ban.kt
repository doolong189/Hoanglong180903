package com.hoanglong180903.vnc_project.model

import com.google.gson.annotations.SerializedName

data class Ban (
    val id : Int = 0,
    @SerializedName("name")
    val ten : String = "",
    val status : Boolean = false
)