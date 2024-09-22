package com.hoanglong180903.vnc_project.model

data class LichSuChanLe (
    private val id : Int = 0,
    val oDatCuoc : String = "",//chẵn hoặc lẻ
    val soTienCuoc : Int = 0,// mức tiền cược
    val soTienNhan: Int = 0,//số tiền thắng hoặc thua
    val thoiGian : String = "",// thời gian cược
    val trangThai : Boolean = false //false là thua , true là thắng
)
