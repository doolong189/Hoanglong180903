package com.hoanglong180903.vnc_project.listener

import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.SanPham

interface OnClickItemHoaDon {
    fun onClickItem(item : HoaDon, position: Int )
}