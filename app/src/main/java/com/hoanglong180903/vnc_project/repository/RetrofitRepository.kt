package com.hoanglong180903.vnc_project.repository

import com.hoanglong180903.vnc_project.network.RetrofitInstance

class RetrofitRepository {

    suspend fun getAllBan(url : String) = RetrofitInstance.api.getAllBan(url)

    suspend fun getAllSanPham(url : String) = RetrofitInstance.api.getAllSanPham(url)
}