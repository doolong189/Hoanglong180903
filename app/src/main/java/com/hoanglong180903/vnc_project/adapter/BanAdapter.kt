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
import com.hoanglong180903.vnc_project.model.HoaDon

class BanAdapter (private var context: Context,
                  private val bans_list: List<Ban>,
                  private val listener: OnClickItemBan,
                  private val listHoaDon: List<HoaDon>) :
    RecyclerView.Adapter<BanAdapter.BanViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION // Lưu vị trí được chọn

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ban, parent, false)
        return BanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BanViewHolder, position: Int) {
        val item = bans_list[position]
        holder.tvBan.text = item.ten

        //new
        val isBanInHoaDon = listHoaDon.any { hoaDon -> hoaDon.ban.id == item.id }

        if (isBanInHoaDon) {
            // tồn tại trong hoaDon
            holder.lnBan.isEnabled = false
            holder.lnBan.setBackgroundColor(Color.GRAY)
        } else {
            // không tồn tại
            holder.lnBan.isEnabled = true
            holder.lnBan.setBackgroundColor(Color.RED)
        }

        if (position == selectedPosition) {
            holder.lnBan.setBackgroundColor(Color.LTGRAY)
        }

        holder.lnBan.setOnClickListener {
            if (holder.lnBan.isEnabled) {
                // Lưu lại vị trí được click
                val previousPosition = selectedPosition
                selectedPosition = holder.adapterPosition

                // Notify để cập nhật lại vị trí cũ và vị trí mới
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                // Gọi listener khi item được click
                listener.onClickItem(item, holder.adapterPosition)
            }
        }
    }

    override fun getItemCount() : Int { return bans_list.size }

    class BanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBan: TextView = itemView.findViewById(R.id.item_ban_tvTen)
        val lnBan : LinearLayout = itemView.findViewById(R.id.item_ban_lnBan)
    }

}