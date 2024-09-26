package com.hoanglong180903.vnc_project.model

import com.google.gson.annotations.SerializedName

data class SanPham (
    val id : Int = 0,
    @SerializedName("name")
    val ten :String = "",
    val image : String = "",
    @SerializedName("price")
    val giaTien : Int = 0,
    var soLuong : Int = 0
)