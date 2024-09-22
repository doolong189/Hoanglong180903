package com.hoanglong180903.vnc_project.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hoanglong180903.vnc_project.R
import com.hoanglong180903.vnc_project.listener.OnClickItemBan
import com.hoanglong180903.vnc_project.listener.OnClickItemSanPham
import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.SanPham

class SanPhamAdapter(
    private var context: Context,
    private var sanPhams_list: List<SanPham>,
    private val listener: OnClickItemSanPham,

    ) :
    RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SanPhamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_san_pham, parent, false)
        return SanPhamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SanPhamViewHolder, position: Int) {
        val item = sanPhams_list[position]
        holder.tvSanPham.text = item.ten
        holder.imgcong.setOnClickListener {
            item.soLuong += 1
            holder.tvSoLuong.text = item.soLuong.toString()
            listener.onClickItem(item, holder.adapterPosition)
        }

        holder.imgTru.setOnClickListener {
            if (item.soLuong == 0) {
                item.soLuong = 0
            } else {
                item.soLuong -= 1
            }
            holder.tvSoLuong.text = item.soLuong.toString()
            listener.onClickItem(item, holder.adapterPosition)
        }
        holder.tvSoLuong.text = item.soLuong.toString()
    }

    override fun getItemCount(): Int {
        return sanPhams_list.size
    }

    class SanPhamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSanPham: TextView = itemView.findViewById(R.id.item_san_pham_tvTen)
        val tvSoLuong: TextView = itemView.findViewById(R.id.item_san_pham_tvSoLuong)
        val imgcong: ImageView = itemView.findViewById(R.id.item_san_pham_imgCong)
        val imgTru: ImageView = itemView.findViewById(R.id.item_san_pham_imgTru)
    }

}