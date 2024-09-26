package com.hoanglong180903.vnc_project.network

import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.SanPham
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getAllBan(@Url url : String) : List<Ban>

    @GET
    suspend fun getAllSanPham(@Url url : String) : List<SanPham>
}