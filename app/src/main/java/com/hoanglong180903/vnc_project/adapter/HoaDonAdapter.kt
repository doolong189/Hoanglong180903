package com.hoanglong180903.vnc_project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoanglong180903.vnc_project.R
import com.hoanglong180903.vnc_project.listener.OnClickItemBan
import com.hoanglong180903.vnc_project.listener.OnClickItemHoaDon
import com.hoanglong180903.vnc_project.model.HoaDon

class HoaDonAdapter(private var context: Context, private val hoaDons_list: List<HoaDon>,
    private val listener : OnClickItemHoaDon) :
    RecyclerView.Adapter<HoaDonAdapter.BanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hoa_don, parent, false)
        return BanViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BanViewHolder, position: Int) {
        val item = hoaDons_list[position]
        holder.tvBan.text = item.ban.ten
        holder.tvStatus.text = if (item.status) "Đã thanh toán" else "Chưa thanh toán"
        holder.tvStatus.visibility = if (item.status) View.VISIBLE else View.GONE
        if (item.status) holder.itemView.isEnabled = false else holder.itemView.isEnabled = true
        val childAdapter: ImageAdapter = ImageAdapter(item.sanPhams)
        holder.rcImage.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
        holder.rcImage.setAdapter(childAdapter)
        holder.rcImage.setHasFixedSize(true)
        childAdapter.notifyDataSetChanged()


        holder.tvSoLuong.text = "Số lượng: ${item.sanPhams.size}"
        var tongTien = 0
        for (x in item.sanPhams) {
            tongTien += (x.giaTien * x.soLuong)
        }
        holder.tvTongTien.text = "$tongTien VNĐ"

        holder.itemView.setOnClickListener {
            listener.onClickItem(item,holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return hoaDons_list.size
    }

    class BanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBan: TextView = itemView.findViewById(R.id.item_hoa_don_tvTen)
        val tvStatus: TextView = itemView.findViewById(R.id.item_hoa_don_tvStatus)
        val rcImage: RecyclerView = itemView.findViewById(R.id.item_hoa_don_rcImage)
        val tvSoLuong: TextView = itemView.findViewById(R.id.item_hoa_don_tvSoLuong)
        val tvTongTien: TextView = itemView.findViewById(R.id.item_hoa_don_tvTongTien)
    }
}
