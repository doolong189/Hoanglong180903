package com.hoanglong180903.vnc_project.model

data class HoaDon (
    val id :Int = 0,
    val ban : Ban ,
    val sanPhams : List<SanPham>,
    val tongTien : Int = 0,
    val ngayTao : String = "",
    val status : Boolean // false : chua thanh toan , true : thanh toan
)