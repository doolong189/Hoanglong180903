package com.hoanglong180903.vnc_project.listener

import com.hoanglong180903.vnc_project.model.Ban

interface OnClickItemBan {
    fun onClickItem(item : Ban, position: Int)
}