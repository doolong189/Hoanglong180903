package com.hoanglong180903.vnc_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hoanglong180903.vnc_project.R

class ChanLeAdapter(private val coinList: List<Boolean>, private val isHeads: Boolean) : RecyclerView.Adapter<ChanLeAdapter.CoinViewHolder>() {

    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinImage: ImageView = itemView.findViewById(R.id.imageViewCoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chan_le, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        // Nếu là heads thì hiển thị hình ảnh cho heads, nếu không thì hiển thị hình ảnh cho tails
        val imageResource = if (isHeads) {
            R.drawable.baseline_circle_24 // thay bằng ảnh heads
        } else {
            R.drawable.baseline_circle_24_3 // thay bằng ảnh tails
        }
        holder.coinImage.setImageResource(imageResource)
    }

    override fun getItemCount(): Int = coinList.size
}