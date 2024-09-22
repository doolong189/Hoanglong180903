package com.hoanglong180903.vnc_project.listener

import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.SanPham

interface OnClickItemSanPham {
    fun onClickItem(item : SanPham, position: Int )
}