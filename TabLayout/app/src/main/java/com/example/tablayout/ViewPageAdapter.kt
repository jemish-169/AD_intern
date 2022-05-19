package com.example.tablayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPageAdapter(
    val images: List<Int>
) : RecyclerView.Adapter<ViewPageAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
    val curImage = images[position]
        holder.itemView.ivImage.setImageResource(curImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}