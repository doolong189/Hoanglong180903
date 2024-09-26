package com.hoanglong180903.vnc_project.model

data class LichSuDatBan (
    val id : Int = 0,
    val ngayDat : String = "",
    val tongTien  : Int = 0,
    val hoadons : List<HoaDon>
)