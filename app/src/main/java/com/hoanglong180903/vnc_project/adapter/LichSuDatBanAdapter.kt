package com.hoanglong180903.vnc_project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hoanglong180903.vnc_project.R
import com.hoanglong180903.vnc_project.listener.OnClickItemBan
import com.hoanglong180903.vnc_project.listener.OnClickItemHoaDon
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.LichSuDatBan

class LichSuDatBanAdapter(private var context: Context, private val list: List<LichSuDatBan>,
    private val listener : OnClickItemHoaDon) :
    RecyclerView.Adapter<LichSuDatBanAdapter.BanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lich_su_dat_ban, parent, false)
        return BanViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BanViewHolder, position: Int) {
        val item = list[position]
        val flexboxLayoutManager = FlexboxLayoutManager(holder.itemView.context)
        flexboxLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val childAdapter: HoaDonAdapter = HoaDonAdapter(holder.itemView.context,item.hoadons,listener)
        holder.rcHoaDon.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        holder.rcHoaDon.setAdapter(childAdapter)
        holder.rcHoaDon.setHasFixedSize(true)
//        holder.rcImage.apply {
//            layoutManager = flexboxLayoutManager
//            adapter = childAdapter
//        }
        childAdapter.notifyDataSetChanged()
        holder.tvTongTien.text = "${item.tongTien} VNĐ"
        holder.tvNgay.text = "Thời gian: "+item.ngayDat
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNgay: TextView = itemView.findViewById(R.id.item_lich_su_tvNgayDat)
        val tvTongTien: TextView = itemView.findViewById(R.id.item_lich_su_tvTongTien)
        val rcHoaDon: RecyclerView = itemView.findViewById(R.id.item_lich_su_rcHoaDon)
    }
}
