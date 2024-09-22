package com.hoanglong180903.vnc_project.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hoanglong180903.vnc_project.R
import com.hoanglong180903.vnc_project.listener.OnClickItemBan
import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.SanPham

class ImageAdapter (private val sanPhamList: List<SanPham>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = sanPhamList[position]
        holder.tvSanPham.text = ""+item.ten+" - "+item.soLuong
    }

    override fun getItemCount() : Int { return sanPhamList.size }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSanPham: TextView = itemView.findViewById(R.id.item_image_tvSoLuong)
    }

}